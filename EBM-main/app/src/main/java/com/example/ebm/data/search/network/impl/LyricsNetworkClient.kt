package com.example.ebm.data.search.network.impl

// RetrofitClient.kt
import com.example.ebm.data.search.network.MusixmatchApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LyricsNetworkClient {
    private const val BASE_URL = "https://api.musixmatch.com/ws/1.1/"

    val instance: MusixmatchApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MusixmatchApi::class.java)
    }
}
