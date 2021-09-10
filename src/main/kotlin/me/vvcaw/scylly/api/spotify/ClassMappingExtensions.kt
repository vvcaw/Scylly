package me.vvcaw.scylly.api.spotify

import com.wrapper.spotify.model_objects.specification.*
import me.vvcaw.scylly.data.*
import java.util.concurrent.TimeUnit

fun Artist.toArtistRecord() = ArtistRecord(
    name = name
)

fun ArtistSimplified.toSimplifiedArtistRecord() = SimplifiedArtistRecord(
    name = name
)

fun Track.toSongRecord() = SongRecord(
    id = id,
    name = name,
    artists = artists.toList().toSimplifiedArtistRecords(),
    images = (album.images.toList()).toStrings(),
    duration = TimeUnit.MILLISECONDS.toMinutes(durationMs.toLong()).toFloat(),
    popularity = popularity,
    explicit = isExplicit,
    openUrl = externalUrls["spotify"],
    playUrl = previewUrl ?: ""
)

fun TrackSimplified.toSimplifiedSongRecord() = SimplifiedSongRecord(
    name = name,
    artists = artists.toList().toSimplifiedArtistRecords(),
)

// For lists
private fun List<Image>.toStrings() : List<String> { return this.map { it.url } }

fun List<ArtistSimplified>.toSimplifiedArtistRecords() : List<SimplifiedArtistRecord> { return this.map { it.toSimplifiedArtistRecord() }.toList() }

fun List<Track>.toSongRecords() : List<SongRecord> { return this.map { it.toSongRecord() }.toList() }

fun List<TrackSimplified>.toSimplifiedSongRecords() : List<SimplifiedSongRecord> { return this.map { it.toSimplifiedSongRecord() }.toList() }