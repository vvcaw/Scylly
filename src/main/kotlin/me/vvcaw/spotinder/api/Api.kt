package me.vvcaw.spotinder.api

import de.elfsoft.javalin.vite.JavalinVite
import de.elfsoft.javalin.vite.ViteHandler
import io.javalin.Javalin
import io.javalin.http.*
import me.vvcaw.spotinder.api.spotify.Spotify
import me.vvcaw.spotinder.api.spotify.toSongRecords
import me.vvcaw.spotinder.data.AddRequest
import me.vvcaw.spotinder.data.UserRecord

class Api(spotify: Spotify, isDev: Boolean, port: Int) {

    init {
        // Setting up the app
        val app = Javalin.create { config ->

            JavalinVite.configure(config, isDev)
        }.start("127.0.0.1", port)

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

        app.get("/", ViteHandler("pages/index.js"))

        app.get("/redirect") { ctx ->
            // Get custom code from url to authorize user
            val code = ctx.queryParam("code") ?: ""

            val user = if (code != "") {
                spotify.authorize(code)
            } else {
                ctx.sessionAttribute<UserRecord>("user") ?: throw UnauthorizedResponse()
            }

            // Safe user object
            ctx.sessionAttribute("user", user)

            ctx.redirect("/discover")
        }

        app.get("/discover",
            ViteHandler("pages/account.js") { ctx ->

                val user = ctx.sessionAttribute<UserRecord>("user") ?: throw UnauthorizedResponse()

                // Get users top songs
                val recommendations = spotify.getSongRecommendations(user.accessToken)

                // Hand over top songs
                mapOf(
                    "recommendations" to recommendations,
                    "user" to user
                )
            }
        )
    }
}