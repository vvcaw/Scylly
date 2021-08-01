package me.vvcaw.hotify

import com.mongodb.client.MongoDatabase
import me.vvcaw.hotify.api.Api
import me.vvcaw.hotify.api.spotify.Spotify
import me.vvcaw.hotify.data.user.UserSongSocket
import me.vvcaw.hotify.util.BackgroundUpdater
import org.litote.kmongo.KMongo
import kotlin.math.log

fun main(args: Array<String>) {
    // Verify if app is in production mode
    val isDevMode = args.isNotEmpty() && "DEV".equals(args[0], true)

    // Generate client for mongo
    val client = KMongo.createClient()
    val database: MongoDatabase = client.getDatabase("hotify")

    // Create logic
    val songSocketLogic = UserSongSocket.getInstance(database)
    val spotifyLogic = Spotify.getInstance(database)

    // Create BackgroundUpdater
    BackgroundUpdater(database, songSocketLogic)

    Api(songSocketLogic, 7000, spotifyLogic, isDevMode)
}