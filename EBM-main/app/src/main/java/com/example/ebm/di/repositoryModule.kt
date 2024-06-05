package com.example.ebm.di

import AndroidMediaPlayerRepositoryImpl
import MediaPlayerRepository
import SearchHistoryRepository
import SearchHistoryRepositoryImpl
import TracksRepository
import TracksRepositoryImpl
import com.example.ebm.data.registration.UserRepository
import com.example.ebm.data.search.impl.PlaylistStorageRepositoryImpl
import com.example.ebm.domain.search.PlaylistStorageRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SearchHistoryRepository>{
        SearchHistoryRepositoryImpl(get())
    }
    single<TracksRepository>{
        TracksRepositoryImpl(get())
    }
    factory<MediaPlayerRepository>{
        AndroidMediaPlayerRepositoryImpl(get())
    }
    factory<PlaylistStorageRepository> {
        PlaylistStorageRepositoryImpl(get())
    }
    single { UserRepository(get()) }
}