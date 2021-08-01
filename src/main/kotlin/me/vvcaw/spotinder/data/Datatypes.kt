package me.vvcaw.spotinder.data

// API
data class SearchRequest(val name: String)
data class AddRequest(val songs: List<SongRecord>)

// Spotify
data class UserRecord(
    val username: String,
    val refreshToken: String, // Should not be emitted like ever
    val accessToken: String, // Should not be emitted like ever
    val profilePictures: List<String>,
    val displayName: String
)

// UserSongSocket

// Complex
data class SongRecord(
    val name: String,
    val artists: List<SimplifiedArtistRecord>,
    val images: List<String>,
    val duration: Float,
    val popularity: Int,
    val explicit: Boolean,
    val openUrl: String,
    val user: String
)

data class ArtistRecord(val name: String)

// Simple
data class SimplifiedSongRecord(val name: String, val artists: List<SimplifiedArtistRecord>)
data class SimplifiedArtistRecord(val name: String)
