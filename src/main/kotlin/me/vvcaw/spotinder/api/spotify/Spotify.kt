package me.vvcaw.spotinder.api.spotify

import com.wrapper.spotify.model_objects.specification.Track
import com.wrapper.spotify.model_objects.specification.User
import me.vvcaw.spotinder.data.SimplifiedSongRecord
import me.vvcaw.spotinder.data.SongRecord
import me.vvcaw.spotinder.data.UserRecord

interface Spotify {
    abstract class LogicException(text: String) : Exception(text)
    class UnauthorizedException : LogicException("Unauthorized")
    class BadRequestException : LogicException("Bad Request")
    class ForbiddenException : LogicException("Forbidden")
    class NotFoundException : LogicException("Not Found")

    fun authorize(code: String): UserRecord
    fun getCurrentUserProfile(accessToken: String): User // Change these to good data classes at some point
    fun getTopSongs(accessToken: String): List<Track> // Change these to good data classes at some point
    fun getSongRecommendations(accessToken: String) : List<SongRecord>

    companion object{
        fun getInstance() : Spotify = SpotifyImplementation()
    }
}