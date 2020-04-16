package com.babblingbrook.mtgcardsearch.ui.search

import androidx.lifecycle.*
import com.babblingbrook.mtgcardsearch.data.Repository
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.Feed
import com.babblingbrook.mtgcardsearch.ui.Status

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val feedUrl = "https://magic.wizards.com/en/rss/rss.xml"

    private val query = MutableLiveData<String>()

    var channel: LiveData<Status<Feed?>> = repository.getFeed(feedUrl).asLiveData()

    fun search(value: String) {
        query.value = value
    }

    val cards: LiveData<Status<List<Card>>> =
        Transformations.switchMap(query) { query -> repository.search(query).asLiveData() }
}
