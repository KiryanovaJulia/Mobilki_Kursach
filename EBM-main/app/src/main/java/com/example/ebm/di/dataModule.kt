package com.example.ebm.di

import ITunesApi
import LocalTrackStorageHandler
import NetworkClient
import RetrofitNetworkClient
import SharedPreferencesLocalTrackStorageHandler
import android.content.Context
import android.media.MediaPlayer
import com.example.ebm.data.registration.RegisterClient
import com.example.ebm.data.registration.UserRepository
import com.example.ebm.data.search.LocalPlaylistStorageHandler
import com.example.ebm.data.search.impl.PlaylistStorageRepositoryImpl
import com.example.ebm.data.search.impl.SharedPreferencesLocalPlaylistStorageHandler
import com.example.ebm.domain.account.KtorApi
import com.example.ebm.domain.search.PlaylistStorageInteractor
import com.example.ebm.domain.search.PlaylistStorageRepository
import com.example.ebm.domain.search.impl.PlaylistStorageInteractorImpl
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val dataModule = module{
    val iTunesBaseUrl = "https://itunes.apple.com"
    val EBM_PREFERENCES = "EBM_PREFERENCES"
    single<ITunesApi>{
        Retrofit.Builder()
            .baseUrl(iTunesBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApi::class.java)
    }
    single{
        androidContext().getSharedPreferences(EBM_PREFERENCES, Context.MODE_PRIVATE)
    }
    single {
        RegisterClient.instance.create(KtorApi::class.java)
    }

    single{
        Gson()
    }
    single<LocalTrackStorageHandler>{
        SharedPreferencesLocalTrackStorageHandler(get(), get())
    }
    single<NetworkClient>{
        RetrofitNetworkClient(get())
    }
    factory{
        MediaPlayer()
    }
    single<LocalPlaylistStorageHandler> {
        SharedPreferencesLocalPlaylistStorageHandler(get(), get())
    }
    factory<PlaylistStorageInteractor> {
        PlaylistStorageInteractorImpl(get())
    }
    factory<PlaylistStorageRepository> {
        PlaylistStorageRepositoryImpl(get())
    }
}