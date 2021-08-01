package me.vvcaw.hotify.api.spotify

import com.mongodb.client.MongoDatabase
import com.wrapper.spotify.model_objects.specification.Track
import com.wrapper.spotify.model_objects.specification.User
import me.vvcaw.hotify.data.SimplifiedSongRecord
import me.vvcaw.hotify.data.SongRecord
import me.vvcaw.hotify.data.UserRecord

interface Spotify {
    abstract class LogicException(text: String) : Exception(text)
    class UnauthorizedException : LogicException("Unauthorized")
    class BadRequestException : LogicException("Bad Request")
    class ForbiddenException : LogicException("Forbidden")
    class NotFoundException : LogicException("Not Found")

    fun authorize(code: String): UserRecord
    fun getCurrentUserProfile(accessToken: String): User // Change these to good data classes at some point
    fun getTopSongs(accessToken: String): List<Track> // Change these to good data classes at some point
    fun getSongRecommendations(accessToken: String) : List<SimplifiedSongRecord>

    companion object{
        fun getInstance(database: MongoDatabase) : Spotify = SpotifyImplementation(database)
    }
}