package com.babblingbrook.mtgcardsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babblingbrook.mtgcardsearch.data.Result
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import com.babblingbrook.mtgcardsearch.ui.Status
import kotlinx.coroutines.launch

class SearchViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    var _cards = MutableLiveData<List<Card>>()
    var cards: LiveData<List<Card>> = _cards
    var _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    fun getCards(query: String) {
        _status.value = Status.LOADING
        viewModelScope.launch {
            if (query.length > 1) {
                val result = scryfallRepository.search(query)
                when (result) {
                    is Result.Success -> {
                        _status.value = Status.SUCCESS
                        _cards.value = result.data
                    }
                    is Result.Error -> {
                        _status.value = Status.FAILED
                        throw result.exception
                    }
                }
            }
        }
    }
}
