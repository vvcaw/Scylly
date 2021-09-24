package me.vvcaw.scylly

import com.beust.klaxon.Klaxon
import me.vvcaw.scylly.api.Api
import me.vvcaw.scylly.api.spotify.Spotify
import me.vvcaw.scylly.data.ClientData
import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    // Verify if app is in production mode
    val isDevMode = args.isNotEmpty() && "DEV".equals(args[0], true)

    val clientData: ClientData? = if(isDevMode) {
        Klaxon().parse<ClientData>(File("config.json").readText(Charsets.UTF_8))
    } else {
        Klaxon().parse<ClientData>(getResourceAsText("/config.json"))
    }

    val spotify = Spotify.getInstance(clientData ?: throw Exception("No config file found!"))

    Api(spotify, isDevMode, 5678)
}

fun getResourceAsText(path: String): String {
    return object {}.javaClass.getResource(path).readText(Charsets.UTF_8)
}