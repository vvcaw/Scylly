package me.vvcaw.scylly.api.spotify

import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.SpotifyHttpManager
import com.wrapper.spotify.model_objects.specification.Track
import com.wrapper.spotify.model_objects.specification.User
import me.vvcaw.scylly.api.spotify.Spotify.UnauthorizedException
import me.vvcaw.scylly.api.spotify.Spotify.BadRequestException
import me.vvcaw.scylly.data.ClientData
import me.vvcaw.scylly.data.SongRecord
import me.vvcaw.scylly.data.UserRecord
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class SpotifyImplementation(clientData: ClientData) : Spotify {

    private val clientId = clientData.clientId
    private val clientSecret = clientData.clientSecret
    private val redirectURI = SpotifyHttpManager.makeUri(clientData.redirectURI)

    private val scheduler = Executors.newScheduledThreadPool(1)

    private val api = SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .setRedirectUri(redirectURI)
        .build()

    private val clientCredentialsRequest = api.clientCredentials()
        .build()

    private val authorizationCodeUriRequest = api.authorizationCodeUri()
        .scope("user-top-read user-library-modify")
        .redirect_uri(redirectURI)
        .show_dialog(true)
        .build()

    init {
        getAuthenticationURI()
        clientCredentials()
    }

    private fun getAuthenticationURI() {
        val uri = authorizationCodeUriRequest.execute();

        println("URI: $uri")
    }

    private fun getUserSpecificAPI(accessToken: String, refreshToken: String): SpotifyApi {

        return SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setAccessToken(accessToken)
            .setRefreshToken(refreshToken)
            .build() ?: throw UnauthorizedException()
    }

    // Update these credentials if they run out!
    private fun clientCredentials() {
        try {
            val clientCredentials = clientCredentialsRequest.execute()

            // Set access token for further use
            api.accessToken = clientCredentials.accessToken

            // Start thread pool to retrieve new token when other one expires using recursion
            scheduler.schedule({
                println("Updated the client credentials!")
                clientCredentials()
            }, clientCredentials.expiresIn.toLong(), TimeUnit.SECONDS)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun authorize(code: String): UserRecord {
        try {
            val authorizationCodeRequest = api.authorizationCode(code)
                .build()

            val authorizationCodeCredentials = authorizationCodeRequest.execute()

            // Set access token for further use (user)
            val accessToken = authorizationCodeCredentials.accessToken
            val refreshToken = authorizationCodeCredentials.refreshToken
            val expiresIn = authorizationCodeCredentials.expiresIn + (System.currentTimeMillis() / 1000) - 10

            return UserRecord(refreshToken, accessToken, expiresIn, 0)
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnauthorizedException()
        }
    }

    override fun getCurrentUserProfile(accessToken: String, refreshToken: String): User {
        val userApi = getUserSpecificAPI(accessToken, refreshToken)

        val request = userApi.currentUsersProfile
            ?.build()

        return request?.execute() ?: throw BadRequestException()
    }

    override fun getTopSongs(accessToken: String, refreshToken: String): List<Track> {
        val userApi = getUserSpecificAPI(accessToken, refreshToken)

        val request = userApi.usersTopTracks
            ?.limit(50)
            ?.time_range("short_term")
            ?.build()

        return request?.execute()?.items?.toList() ?: throw BadRequestException()
    }

    override fun getSongRecommendations(
        accessToken: String,
        refreshToken: String,
        amount: Int,
        topSongs: List<Track>
    ): List<SongRecord> {
        val userApi = getUserSpecificAPI(accessToken, refreshToken)

        // Think about relevant collection and amount of requests to this endpoint due to less diverse song recommendations
        val topSongsArtistSeeds = topSongs.map { it.artists.first().id }.shuffled().subList(0, 1).joinToString(",")
        val topSongsTrackSeeds = topSongs.map { it.id }.shuffled().subList(0, 2).joinToString(",")

        val request = userApi.recommendations
            ?.limit(amount)
            ?.seed_artists(topSongsArtistSeeds)
            ?.seed_tracks(topSongsTrackSeeds)
            ?.build()

        val songs = request?.execute()?.tracks?.mapNotNull { getTrack(it.id, accessToken, refreshToken) }
            ?: throw BadRequestException()

        return songs.toSongRecords()
    }

    override fun likeSong(accessToken: String, refreshToken: String, id: String) {
        val userApi = getUserSpecificAPI(accessToken, refreshToken)

        val request = userApi.saveTracksForUser(id)
            .build()

        request.execute()
    }

    override fun refreshAccessToken(u: UserRecord): UserRecord {
        val userApi = getUserSpecificAPI(u.accessToken, u.refreshToken)

        val updateToken = userApi.authorizationCodeRefresh()
            .grant_type("refresh_token")
            .refresh_token(u.refreshToken)
            .build()

        val credentials = updateToken.execute()
        val expiresIn = credentials.expiresIn + (System.currentTimeMillis() / 1000) - 10

        return UserRecord(u.refreshToken, credentials.accessToken, expiresIn, u.refreshTopSongs)
    }

    private fun getTrack(songId: String, accessToken: String, refreshToken: String): Track {
        val userApi = getUserSpecificAPI(accessToken, refreshToken)

        val request = userApi.getTrack(songId)
            ?.build()

        return request?.execute() ?: throw BadRequestException()
    }
}