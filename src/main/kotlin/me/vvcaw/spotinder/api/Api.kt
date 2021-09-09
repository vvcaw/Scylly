package me.vvcaw.spotinder.api

import com.wrapper.spotify.model_objects.specification.Track
import de.elfsoft.javalin.vite.JavalinVite
import de.elfsoft.javalin.vite.ViteHandler
import io.javalin.Javalin
import io.javalin.http.*
import me.vvcaw.spotinder.api.spotify.Spotify
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
            ViteHandler("pages/discover.js") { ctx ->
                var user = ctx.sessionAttribute<UserRecord>("user") ?: throw UnauthorizedResponse()
                val validateUser = validateAccessToken(user, spotify)

                if (validateUser != null) {
                    println("Updated $user to $validateUser because the access token expired!")
                    user = validateUser
                    ctx.sessionAttribute("user", validateUser)
                }

                val topSongs = ctx.getTopSongs(user, spotify)

                // Get users top songs
                val recommendations = spotify.getSongRecommendations(user.accessToken, user.refreshToken, 15, topSongs)

                // Hand over top songs
                mapOf(
                    "recommendations" to recommendations,
                    "user" to user
                )
            }
        )

        app.get("/api/recommendations") { ctx ->
            var user = ctx.sessionAttribute<UserRecord>("user") ?: throw UnauthorizedResponse()
            val validateUser = validateAccessToken(user, spotify)

            if (validateUser != null) {
                println("Updated $user to $validateUser because the access token expired!")
                user = validateUser
                ctx.sessionAttribute("user", validateUser)
            }

            val topSongs = ctx.getTopSongs(user, spotify)

            // Get users top songs
            val recommendations = spotify.getSongRecommendations(user.accessToken, user.refreshToken, 10, topSongs)

            ctx.json(recommendations)
        }

        app.post("/api/like/:song_id") { ctx ->
            var user = ctx.sessionAttribute<UserRecord>("user") ?: throw UnauthorizedResponse()
            val validateUser = validateAccessToken(user, spotify)

            if (validateUser != null) {
                println("Updated $user to $validateUser because the access token expired!")
                user = validateUser
                ctx.sessionAttribute("user", validateUser)
            }

            spotify.likeSong(user.accessToken, user.refreshToken, ctx.pathParam("song_id"))
        }
    }

    // Check if a user's access token is still valid and update if it isn't
    private fun validateAccessToken(u: UserRecord, spotify: Spotify): UserRecord? {
        if (u.expiresAt < (System.currentTimeMillis() / 1000)) {
            return spotify.refreshAccessToken(u)
        }

        return null
    }

    // These might need to be updated more often - If session is really long > 2 weeks or some stuff
    fun Context.getTopSongs(user: UserRecord, spotify: Spotify): List<Track> {
        val topSongs = this.sessionAttribute<List<Track>>("topSongs")
        val currentTimeSeconds = System.currentTimeMillis() / 1000

        return if (user.refreshTopSongs < currentTimeSeconds || topSongs == null) {
            val songs = spotify.getTopSongs(user.accessToken, user.refreshToken)

            // Update user to keep track on when to refresh songs again
            this.sessionAttribute("topSongs", songs)

            // Update songs after 1 week
            val newUser = UserRecord(user.refreshToken, user.accessToken, user.expiresAt, currentTimeSeconds + 604800)
            this.sessionAttribute("user", newUser)
            println("Updated top songs for user $user")
            songs
        } else {
            topSongs
        }
    }
}