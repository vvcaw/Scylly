package me.vvcaw.hotify.util

import com.mongodb.client.MongoDatabase
import me.vvcaw.hotify.api.spotify.Spotify
import me.vvcaw.hotify.api.spotify.toSongRecords
import me.vvcaw.hotify.data.SongRecord
import me.vvcaw.hotify.data.UserRecord
import me.vvcaw.hotify.data.user.UserSongSocket
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BackgroundUpdater(val database: MongoDatabase, val logic: UserSongSocket) {

    private val scheduler = Executors.newScheduledThreadPool(2)

    // Create new instances, thread doesn't effect other instance with traffic
    private val spotify = Spotify.getInstance(database)

    init {
        scheduler.scheduleAtFixedRate({
            println()
            print("Started trending update...")
            updateTrending()
            print("...finished!")
        }, 0, 5, TimeUnit.MINUTES)

        scheduler.scheduleAtFixedRate({
            println()
            print("Started song update...")
            updateSongs()
            print("...finished!")
        }, 0, 1, TimeUnit.DAYS)
    }

    private fun updateSongs() {
        val userCol = database.getCollection<UserRecord>()
        val songCol = database.getCollection<SongRecord>()

        val users = userCol.find().toList()

        // Get songs correspondent to user -> remove old ones and add new ones
        users.forEach { user ->
            songCol.deleteMany(SongRecord::user.eq(user.username))

            val songs = spotify.getTopSongs(user.accessToken).toSongRecords(user.username)
            logic.addSongs(songs)
        }
    }

    private fun updateTrending() {
        val col = database.getCollection<SongRecord>()

        // Cursor class for iterating through collection {url} is unique -> key
        data class Cursor(
            @BsonId val url: String,
            val count: Int
        )

        // Grouping songs and sorting by count -> map to full object
        val trendingListNew = col.aggregate<Cursor>(
            group(
                SongRecord::openUrl,
                Cursor::count sum 1
            ),
            sort(
                descending(
                    Cursor::count
                )
            )
        ).mapNotNull { cursor -> col.findOne(SongRecord::openUrl.eq(cursor.url)) }.toList()

        logic.updateTrending(trendingListNew)
    }
}