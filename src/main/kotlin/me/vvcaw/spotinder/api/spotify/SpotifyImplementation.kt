package me.vvcaw.hotify.api.spotify

import com.mongodb.client.MongoDatabase
import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.SpotifyHttpManager
import com.wrapper.spotify.model_objects.specification.Track
import com.wrapper.spotify.model_objects.specification.User
import me.vvcaw.hotify.api.spotify.Spotify.UnauthorizedException
import me.vvcaw.hotify.api.spotify.Spotify.BadRequestException
import me.vvcaw.hotify.data.SimplifiedSongRecord
import me.vvcaw.hotify.data.UserRecord
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

internal class SpotifyImplementation(val database: MongoDatabase) : Spotify {

    private val clientId = "ca135148aceb494cb629d07939b71f7c"
    private val clientSecret = "35a2dd046e2549eca82bb366ff7b5764"
    private val redirectURI = SpotifyHttpManager.makeUri("http://localhost:7000/spotify-redirect")

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

    private fun getUserSpecificAPI(accessToken: String): SpotifyApi? {
        return SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build()
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

            // Build UserRecord and safe in mongo
            val user = UserRecord(username, refreshToken, accessToken, profilePictures, displayName)

            val col = database.getCollection<UserRecord>()

            // Only insert user if it's his first login
            if (col.findOne(UserRecord::username.eq(username)) == null) {
                col.insertOne(user)
            }

            return user
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnauthorizedException()
        }
    }

    override fun getCurrentUserProfile(accessToken: String): User {
        val userApi = getUserSpecificAPI(accessToken)

        val request = userApi?.currentUsersProfile
            ?.build()

        return request?.execute() ?: throw UnauthorizedException()
    }

    override fun getTopSongs(accessToken: String): List<Track> {
        val userApi = getUserSpecificAPI(accessToken)

        val request = userApi?.usersTopTracks
            ?.limit(50)
            ?.time_range("short_term")
            ?.build()

        return request?.execute()?.items?.toList() ?: throw UnauthorizedException()
    }

    override fun getSongRecommendations(accessToken: String): List<SimplifiedSongRecord> {
        val userApi = getUserSpecificAPI(accessToken)

        val topSongs = getTopSongs(accessToken)

        // Think about relevant collection and amount of requests to this endpoint due to less diverse song recommendations
        val topSongsArtistSeeds = topSongs.map { it.artists.first().id }.shuffled().subList(0, 1).joinToString(",")
        val topSongsTrackSeeds = topSongs.map { it.id }.shuffled().subList(0, 2).joinToString(",")

        val request = userApi?.recommendations
            ?.seed_artists(topSongsArtistSeeds)
            ?.seed_tracks(topSongsTrackSeeds)
            ?.build()

        val songs = request?.execute()?.tracks?.toList() ?: throw BadRequestException()

        return songs.toSimplifiedSongRecords()
    }
}