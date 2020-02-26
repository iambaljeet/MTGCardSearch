package com.babblingbrook.mtgcardsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import com.babblingbrook.mtgcardsearch.data.NetworkState
import com.babblingbrook.mtgcardsearch.data.DataSourceFactory
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import com.babblingbrook.mtgcardsearch.util.pagedListConfig

class SearchViewModel(private val scryfallRepository: ScryfallRepository) : ViewModel() {

    private val cardDataSource =
        DataSourceFactory(
            scryfallRepository = scryfallRepository,
            scope = viewModelScope
        )

    val cards = LivePagedListBuilder(cardDataSource, pagedListConfig()).build()
    val networkState: LiveData<NetworkState>? =
        switchMap(cardDataSource.source) { it.getNetworkState() }

    fun search(query: String) {
        cardDataSource.updateQuery(query)
    }

    fun refreshFailedRequest() = cardDataSource.getSource()?.retryFailedQuery()
}
