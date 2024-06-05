package com.example.ebm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.example.ebm.R
import com.example.ebm.data.search.dto.LyricsResponse
import com.example.ebm.domain.search.models.Track
import com.example.ebm.data.search.dto.TrackSearchResponse
import com.example.ebm.data.search.network.impl.LyricsNetworkClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LyricsActivity : AppCompatActivity() {
    private val apiKey = "c84eb1368019c79f0651f35c1e02cc5d"
    private lateinit var lyricsTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics)
        lyricsTv = findViewById(R.id.lyrics_tv)
        val backbtn = findViewById<ImageButton>(R.id.back_btn)
        backbtn.setOnClickListener {
            finish()
        }
        val track = Gson().fromJson(intent.getStringExtra(LYRICS_KEY), Track::class.java)
        getTrackId(track.trackName)

    }

    private fun getTrackId(trackName: String) {
        LyricsNetworkClient.instance.searchTrack(trackName, apiKey).enqueue(object :
            Callback<TrackSearchResponse> {
            override fun onResponse(call: Call<TrackSearchResponse>, response: Response<TrackSearchResponse>) {
                if (response.isSuccessful) {
                    Log.i("Response", "Success")
                    val trackId = response.body()?.message?.body?.track_list?.firstOrNull()?.track?.track_id
                    if (trackId != null) {
                        Log.i("ID", trackId.toString())
                        getLyrics(trackId)
                    }
                }
            }

            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                Log.i("Дикий фейл братан", "лоол")
            }
        })
    }

    private fun getLyrics(trackId: Int) {
        LyricsNetworkClient.instance.getLyrics(trackId, apiKey).enqueue(object : Callback<LyricsResponse> {
            override fun onResponse(call: Call<LyricsResponse>, response: Response<LyricsResponse>) {
                if (response.isSuccessful) {
                    val lyrics = response.body()?.message?.body?.lyrics?.lyrics_body
                    if (lyrics != null) {
                        lyricsTv.text = lyrics
                    }
                }
            }
            override fun onFailure(call: Call<LyricsResponse>, t: Throwable) {
                lyricsTv.text = getString(R.string.song_lyrics_not_found)
            }
        })
    }
    companion object{
        const val LYRICS_KEY = "LYRICS_KEY"
    }
}