package com.babblingbrook.mtgcardsearch.di

import com.babblingbrook.mtgcardsearch.ui.detail.DetailViewModel
import com.babblingbrook.mtgcardsearch.ui.favorites.FavoritesViewModel
import com.babblingbrook.mtgcardsearch.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
}