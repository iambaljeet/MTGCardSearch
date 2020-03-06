package com.babblingbrook.mtgcardsearch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babblingbrook.mtgcardsearch.data.Repository
import com.babblingbrook.mtgcardsearch.model.Card
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> get() = _card

    fun setCardData(card: Card?) {
        _card.value = card
    }

    fun addFavorite(card: Card) {
        viewModelScope.launch {
            try {
                card.isFavorite = true
                repository.addFavorite(card)
            } finally {
                _card.postValue(card)
            }
        }
    }

    fun removeFavorite(card: Card) {
        viewModelScope.launch {
            try {
                card.isFavorite = false
                repository.removeFavorite(card.name)
            } finally {
                _card.postValue(card)
            }
        }
    }
}
