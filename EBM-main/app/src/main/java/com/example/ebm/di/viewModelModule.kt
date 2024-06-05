package com.example.ebm.di

import PlayerViewModel
import com.example.ebm.ui.main.MainViewModel
import com.example.ebm.ui.search.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        SearchViewModel(get(), get(), get())
    }
    viewModel {
        PlayerViewModel(get(), get())
    }
    viewModel {
        MainViewModel(get())
    }
}