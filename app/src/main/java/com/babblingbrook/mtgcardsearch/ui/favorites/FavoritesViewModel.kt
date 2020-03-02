package com.babblingbrook.mtgcardsearch.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import com.babblingbrook.mtgcardsearch.ui.Status
import com.babblingbrook.mtgcardsearch.data.Result
import kotlinx.coroutines.launch

class FavoritesViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    var _cards = MutableLiveData<List<Card>>()
    var cards: LiveData<List<Card>> = _cards
    var _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    fun getCards() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            _cards.value = scryfallRepository.getFavorites()
            _status.value = Status.SUCCESS
        }
    }
}
