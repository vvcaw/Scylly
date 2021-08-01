package me.vvcaw.hotify.data.user

import com.mongodb.client.MongoDatabase
import me.vvcaw.hotify.data.SongRecord

interface UserSongSocket {

    abstract class LogicException(text: String) : Exception(text)
    class UnauthorizedException : LogicException("Unauthorized")
    class ForbiddenException : LogicException("Forbidden")
    class NotFoundException : LogicException("Not Found")

    fun getTrending(limit: Int) : List<SongRecord>
    fun addSongs(songs: List<SongRecord>)
    fun usersSongsExist(username: String) : Boolean
    fun updateTrending(songs: List<SongRecord>)

    companion object{
        fun getInstance(database: MongoDatabase) : UserSongSocket = UserSongSocketImplementation(database)
    }
}