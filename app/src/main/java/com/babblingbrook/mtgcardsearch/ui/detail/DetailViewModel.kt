package com.babblingbrook.mtgcardsearch.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    private val _card = MutableLiveData<Card>()

    val card: LiveData<Card> get() = _card

    fun setCardData(card: Card?) {
        _card.value = card
        _isFavorite.value = card?.isFavorite
    }

    private val _isFavorite = MutableLiveData<Boolean>()

    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun addFavorite(card: Card) {
        viewModelScope.launch {
            card.isFavorite = true
            scryfallRepository.addFavorite(card)
            _isFavorite.value = true
        }
    }

    fun removeFavorite(card: Card) {
        viewModelScope.launch {
            scryfallRepository.removeFavorite(card.name)
            _isFavorite.value = false
        }
    }

}
