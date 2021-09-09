package me.vvcaw.spotinder.data

// API
data class AddRequest(val songs: List<SongRecord>)
data class ClientData(val clientId: String, val clientSecret: String, val redirectURI: String)

// Spotify
data class UserRecord(
    val refreshToken: String, // Should not be emitted like ever
    val accessToken: String, // Should not be emitted like ever
    val expiresAt: Long,
    val refreshTopSongs: Long
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
    val playUrl: String
)

data class ArtistRecord(val name: String)

// Simple
data class SimplifiedSongRecord(val name: String, val artists: List<SimplifiedArtistRecord>)
data class SimplifiedArtistRecord(val name: String)
