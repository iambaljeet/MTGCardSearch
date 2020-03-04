package com.babblingbrook.mtgcardsearch.ui.detail

import androidx.lifecycle.*
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    private val _card = MutableLiveData<Card>()
    val card: LiveData<Card> get() = _card

    fun setCardData(card: Card?) {
        _card.value = card
    }

    fun addFavorite(card: Card) {
        viewModelScope.launch {
            try {
                card.isFavorite = true
                scryfallRepository.addFavorite(card)
            } finally {
                _card.postValue(card)
            }
        }
    }

    fun removeFavorite(card: Card) {
        viewModelScope.launch {
            try {
                card.isFavorite = false
                scryfallRepository.removeFavorite(card.name)
            } finally {
                _card.postValue(card)
            }
        }
    }
}
