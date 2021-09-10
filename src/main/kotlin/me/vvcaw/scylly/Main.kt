package me.vvcaw.scylly

import com.beust.klaxon.Klaxon
import me.vvcaw.scylly.api.Api
import me.vvcaw.scylly.api.spotify.Spotify
import me.vvcaw.scylly.data.ClientData
import java.io.File

fun main(args: Array<String>) {
    // Verify if app is in production mode
    val isDevMode = args.isNotEmpty() && "DEV".equals(args[0], true)

    val clientData = Klaxon().parse<ClientData>(File("config.json").readText(Charsets.UTF_8)) ?: throw Exception("No config file found!")

    val spotify = Spotify.getInstance(clientData)

    Api(spotify, isDevMode, 7000)
}