package com.example.ebm.data.search.network

import com.example.ebm.data.search.dto.LyricsResponse
import com.example.ebm.data.search.dto.TrackSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MusixmatchApi {
    @GET("track.search")
    fun searchTrack(
        @Query("q_track") trackName: String,
        @Query("apikey") apiKey: String
    ): Call<TrackSearchResponse>

    @GET("track.lyrics.get")
    fun getLyrics(
        @Query("track_id") trackId: Int,
        @Query("apikey") apiKey: String
    ): Call<LyricsResponse>
}