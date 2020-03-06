package com.babblingbrook.mtgcardsearch.ui.search

import androidx.lifecycle.*
import com.babblingbrook.mtgcardsearch.data.Repository
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.Feed
import com.babblingbrook.mtgcardsearch.ui.Status

class SearchViewModel(private val repository: Repository) : ViewModel() {

    private val feedUrl = "https://magic.wizards.com/en/rss/rss.xml"

    private val query = MutableLiveData<String>()

    var channel: LiveData<Status<Feed?>> = getFeeds()

    private fun getFeeds(): LiveData<Status<Feed?>> {
        return repository.getFeed(feedUrl).asLiveData()
    }

    fun search(value: String) {
        query.value = value
    }

    val cards: LiveData<Status<List<Card>>> =
        Transformations.switchMap(query) { query -> getCards(query) }

    private fun getCards(query: String): LiveData<Status<List<Card>>> {
        return repository.search(query).asLiveData()
    }
}
