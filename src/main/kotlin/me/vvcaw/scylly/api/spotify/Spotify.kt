package me.vvcaw.scylly.api.spotify

import com.wrapper.spotify.model_objects.specification.Track
import com.wrapper.spotify.model_objects.specification.User
import me.vvcaw.scylly.data.ClientData
import me.vvcaw.scylly.data.SongRecord
import me.vvcaw.scylly.data.UserRecord

interface Spotify {
    abstract class LogicException(text: String) : Exception(text)
    class UnauthorizedException : LogicException("Unauthorized")
    class BadRequestException : LogicException("Bad Request")
    class ForbiddenException : LogicException("Forbidden")
    class NotFoundException : LogicException("Not Found")

    fun authorize(code: String): UserRecord
    fun getCurrentUserProfile(accessToken: String, refreshToken: String): User // Change these to good data classes at some point
    fun getTopSongs(accessToken: String, refreshToken: String): List<Track> // Change these to good data classes at some point
    fun getSongRecommendations(accessToken: String, refreshToken: String, amount: Int, topSongs: List<Track>) : List<SongRecord>
    fun likeSong(accessToken: String, refreshToken: String, id: String)
    fun refreshAccessToken(u: UserRecord) : UserRecord

    companion object{
        fun getInstance(clientData: ClientData) : Spotify = SpotifyImplementation(clientData)
    }
}