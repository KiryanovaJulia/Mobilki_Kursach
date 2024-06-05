package com.example.ebm.data.registration

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RegisterClient {
    private const val BASE_URL = "http://10.0.2.2:8080"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}