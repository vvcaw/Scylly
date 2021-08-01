package me.vvcaw.hotify.data.user

import com.mongodb.client.MongoDatabase
import me.vvcaw.hotify.api.spotify.Spotify
import me.vvcaw.hotify.api.spotify.toSongRecords
import me.vvcaw.hotify.data.SongRecord
import me.vvcaw.hotify.data.UserRecord
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

internal class UserSongSocketImplementation(val database: MongoDatabase) : UserSongSocket {

    private var trendingList = listOf<SongRecord>()

    init {
        // Speeds up grouping by {openUrl}
        database.getCollection<SongRecord>().ensureIndex(SongRecord::openUrl)
    }

    // returns the top {limit} songs listened through the row
    override fun getTrending(limit: Int): List<SongRecord> {
        if (limit <= trendingList.size) {
            return trendingList.subList(0, limit - 1)
        }

        return trendingList
    }

    override fun updateTrending(songs: List<SongRecord>) {
        trendingList = songs
    }

    override fun addSongs(songs: List<SongRecord>) {
        val col = database.getCollection<SongRecord>()

        col.insertMany(songs)
    }

    override fun usersSongsExist(username: String): Boolean {
        val col = database.getCollection<SongRecord>()

        return col.findOne(SongRecord::user.eq(username)) != null
    }
}