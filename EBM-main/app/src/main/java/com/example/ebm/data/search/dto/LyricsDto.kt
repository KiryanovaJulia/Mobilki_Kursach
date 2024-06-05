package com.example.ebm.data.search.dto

data class TrackSearchResponse(
    val message: Message
)

data class Message(
    val body: Body
)

data class Body(
    val track_list: List<TrackContainer>
)

data class TrackContainer(
    val track: Track
)

data class Track(
    val track_id: Int
)

// LyricsResponse.kt
data class LyricsResponse(
    val message: LyricsMessage
)

data class LyricsMessage(
    val body: LyricsBody
)

data class LyricsBody(
    val lyrics: Lyrics
)

data class Lyrics(
    val lyrics_body: String
)
