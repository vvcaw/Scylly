package me.vvcaw.hotify.api

import de.elfsoft.javalin.vite.JavalinVite
import de.elfsoft.javalin.vite.ViteHandler
import io.javalin.Javalin
import io.javalin.http.*
import me.vvcaw.hotify.api.spotify.Spotify
import me.vvcaw.hotify.api.spotify.SpotifyImplementation
import me.vvcaw.hotify.api.spotify.toSongRecords
import me.vvcaw.hotify.data.AddRequest
import me.vvcaw.hotify.data.UserRecord
import me.vvcaw.hotify.data.user.UserSongSocket
import me.vvcaw.hotify.util.secureRandom

class Api(logic: UserSongSocket, port: Int, spotify: Spotify, isDev: Boolean) {

    init {
        // Setting up the app
        val app = Javalin.create { config ->

            JavalinVite.configure(config, isDev)
        }.start("127.0.0.1", port)

        app.exception(UserSongSocket.LogicException::class.java) { e, ctx ->
            val response = when (e) {
                is UserSongSocket.NotFoundException -> NotFoundResponse()
                is UserSongSocket.UnauthorizedException -> UnauthorizedResponse()
                is UserSongSocket.ForbiddenException -> ForbiddenResponse()
                else -> InternalServerErrorResponse("Api Exception not found")
            }

            ctx.status(response.status)
            ctx.result(response.message ?: "")
        }

        app.exception(Spotify.LogicException::class.java) { e, ctx ->
            val response = when (e) {
                is Spotify.NotFoundException -> NotFoundResponse()
                is Spotify.UnauthorizedException -> UnauthorizedResponse()
                is Spotify.ForbiddenException -> ForbiddenResponse()
                is Spotify.BadRequestException -> BadRequestResponse()
                else -> InternalServerErrorResponse("Api Exception not found")
            }

            ctx.status(response.status)
            ctx.result(response.message ?: "")
        }

        app.get("/spotify-redirect",
            ViteHandler("pages/account.js") { ctx ->

                // Get custom code from url to authorize user
                val code = ctx.queryParam("code") ?: ""

                // Only call .authorize if user is logging in from spotify, otherwise get user object from session
                val user = if(code != ""){
                    spotify.authorize(code)
                }
                else{
                    ctx.sessionAttribute<UserRecord>("user") ?: throw UnauthorizedResponse()
                }

                // Get users top songs
                val topSongs = spotify.getTopSongs(user.accessToken)
                val topSongsRecords = topSongs.toSongRecords(user.username)

                // Also save songs to mongo if user is not present in database, else skip this step (redundant songs)
                if(!logic.usersSongsExist(user.username))
                    logic.addSongs(topSongsRecords)

                // Safe user object and topSongs list in session
                ctx.sessionAttribute("user", user)

                // Hand over top songs
                mapOf(
                    "topSongs" to topSongsRecords,
                    "user" to user
                )
        })

        app.get("/", ViteHandler("pages/overview.js"))

        // testing for tinder thingi
        app.get("/test", ViteHandler("pages/test.js"))

        app.get("/api/recommendations") { ctx ->
            val user = ctx.sessionAttribute<UserRecord>("user") ?: throw UnauthorizedResponse()

            val recommendations = spotify.getSongRecommendations(user.accessToken)

            /* mapOf(
                "recommendations" to recommendations
            )

             */

            ctx.result(recommendations.joinToString(" | "))
        }

        app.get("/api/trending") { ctx ->
            val trending = logic.getTrending(20)

            if(trending.isEmpty())
                throw InternalServerErrorResponse("List is empty")

            val song = trending[secureRandom.nextInt(trending.size)]

            ctx.json(song)
        }

        app.put("/api/trending/add") { ctx ->
            val addData = ctx.bodyAsClass(AddRequest::class.java)

            logic.addSongs(addData.songs)
        }
    }
}