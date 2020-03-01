package com.babblingbrook.mtgcardsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babblingbrook.mtgcardsearch.data.Result
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import com.babblingbrook.mtgcardsearch.ui.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    var _cards = MutableLiveData<List<Card>>()
    var cards: LiveData<List<Card>> = _cards
    var _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun getCards(query: String) {
        _viewState.value = ViewState.LOADING
        viewModelScope.launch {
            if (query.length > 1) {
                val result = scryfallRepository.getCards(query)
                when (result) {
                    is Result.Success -> {
                        _viewState.value = ViewState.SUCCESS
                        _cards.value = result.data
                    }
                    is Result.Error -> {
                        _viewState.value = ViewState.FAILED
                        throw result.exception
                    }
                }
            }
        }
    }
}
