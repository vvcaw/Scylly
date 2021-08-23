package me.vvcaw.spotinder.api.spotify

import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.SpotifyHttpManager
import com.wrapper.spotify.model_objects.specification.Track
import com.wrapper.spotify.model_objects.specification.User
import me.vvcaw.spotinder.api.spotify.Spotify.UnauthorizedException
import me.vvcaw.spotinder.api.spotify.Spotify.BadRequestException
import me.vvcaw.spotinder.data.SimplifiedSongRecord
import me.vvcaw.spotinder.data.SongRecord
import me.vvcaw.spotinder.data.UserRecord

internal class SpotifyImplementation() : Spotify {

    private val clientId = "379ef201c377495cb09a99464e0e49df"
    private val clientSecret = "498f0eb1ed6d4a80b2fc6110680a2a04"
    private val redirectURI = SpotifyHttpManager.makeUri("http://localhost:7000/redirect")

    private val api = SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .setRedirectUri(redirectURI)
        .build()

    private val clientCredentialsRequest = api.clientCredentials()
        .build()

    private val authorizationCodeUriRequest = api.authorizationCodeUri()
        .scope("user-read-recently-played user-top-read")
        .redirect_uri(redirectURI)
        .show_dialog(true)
        .build()

    init {
        // Activate on prod
        clientCredentials()
        getAuthenticationURI()
    }

    private fun getAuthenticationURI() {
        val uri = authorizationCodeUriRequest.execute();

        println("URI: $uri");
    }

    private fun getUserSpecificAPI(accessToken: String): SpotifyApi {
        return SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build() ?: throw UnauthorizedException()
    }

    private fun clientCredentials() {
        try {
            val clientCredentials = clientCredentialsRequest.execute()

            // Set access token for further use
            api.accessToken = clientCredentials.accessToken
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

            val userProfile = getCurrentUserProfile(accessToken)
            val profilePictures = userProfile.images.map { it.url }
            val displayName = userProfile.displayName
            val username = userProfile.id

            return UserRecord(username, refreshToken, accessToken, profilePictures, displayName)
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnauthorizedException()
        }
    }

    override fun getCurrentUserProfile(accessToken: String): User {
        val userApi = getUserSpecificAPI(accessToken)

        val request = userApi.currentUsersProfile
            ?.build()

        return request?.execute() ?: throw BadRequestException()
    }

    override fun getTopSongs(accessToken: String): List<Track> {
        val userApi = getUserSpecificAPI(accessToken)

        val request = userApi.usersTopTracks
            ?.limit(50)
            ?.time_range("short_term")
            ?.build()

        return request?.execute()?.items?.toList() ?: throw BadRequestException()
    }

    override fun getSongRecommendations(accessToken: String, amount: Int, topSongs: List<Track>): List<SongRecord> {
        val userApi = getUserSpecificAPI(accessToken)

        // Think about relevant collection and amount of requests to this endpoint due to less diverse song recommendations
        val topSongsArtistSeeds = topSongs.map { it.artists.first().id }.shuffled().subList(0, 1).joinToString(",")
        val topSongsTrackSeeds = topSongs.map { it.id }.shuffled().subList(0, 2).joinToString(",")

        val request = userApi.recommendations
            ?.limit(amount)
            ?.seed_artists(topSongsArtistSeeds)
            ?.seed_tracks(topSongsTrackSeeds)
            ?.build()

        val songs = request?.execute()?.tracks?.mapNotNull { getTrack(it.id, accessToken) } ?: throw BadRequestException()

        return songs.toSongRecords()
    }

    private fun getTrack(songId: String, accessToken: String): Track {
        val userApi = getUserSpecificAPI(accessToken)

        val request = userApi.getTrack(songId)
            ?.build()

        return request?.execute() ?: throw BadRequestException()
    }
}