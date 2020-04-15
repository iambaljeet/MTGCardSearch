package com.babblingbrook.mtgcardsearch.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.babblingbrook.mtgcardsearch.data.Repository
import com.babblingbrook.mtgcardsearch.model.Card

class FavoritesViewModel(repository: Repository) : ViewModel() {

    var cards: LiveData<List<Card>> = repository.getFavorites().asLiveData()
}
