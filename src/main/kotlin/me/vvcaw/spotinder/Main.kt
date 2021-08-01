package me.vvcaw.spotinder

import me.vvcaw.spotinder.api.Api
import me.vvcaw.spotinder.api.spotify.Spotify

fun main(args: Array<String>) {
    // Verify if app is in production mode
    val isDevMode = args.isNotEmpty() && "DEV".equals(args[0], true)

    val spotify = Spotify.getInstance()

    Api(spotify, isDevMode, 7000)
}