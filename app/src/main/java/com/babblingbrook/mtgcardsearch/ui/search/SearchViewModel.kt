package com.babblingbrook.mtgcardsearch.ui.search

import androidx.lifecycle.*
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import com.babblingbrook.mtgcardsearch.ui.Status

class SearchViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    private val query = _query

    fun search(value: String) {
        _query.value = value
    }

    val cards: LiveData<Status<List<Card>>> =
        Transformations.switchMap(query) { query -> getCards(query) }

    fun getCards(query: String): LiveData<Status<List<Card>>> {
        return scryfallRepository.search(query).asLiveData()
    }


}
