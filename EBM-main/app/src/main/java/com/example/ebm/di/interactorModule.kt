package com.example.ebm.di

import PlayerInteractor
import PlayerInteractorImpl
import SearchHistoryInteractor
import SearchHistoryInteractorImpl
import TracksInteractor
import TracksInteractorImpl
import com.example.ebm.domain.search.PlaylistStorageInteractor
import com.example.ebm.domain.search.impl.PlaylistStorageInteractorImpl
import org.koin.dsl.module

val interactorModule = module{
    factory<SearchHistoryInteractor>{
        SearchHistoryInteractorImpl(get())
    }
    factory<TracksInteractor>{
        TracksInteractorImpl(get())
    }
    factory<PlayerInteractor>{
        PlayerInteractorImpl(get())
    }
    factory<PlaylistStorageInteractor> {
        PlaylistStorageInteractorImpl(get())
    }
}