package com.babblingbrook.mtgcardsearch.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.babblingbrook.mtgcardsearch.data.CardDataSource
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import kotlinx.coroutines.CoroutineScope


class DataSourceFactory(
    private val scryfallRepository: ScryfallRepository,
    private var query: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Card>() {
    val source = MutableLiveData<CardDataSource>()

    override fun create(): DataSource<Int, Card> {
        val source = CardDataSource(
            scryfallRepository,
            query,
            scope
        )
        this.source.postValue(source)
        return source
    }

    fun getSource() = source.value

    fun updateQuery(query: String) {
        this.query = query
        getSource()?.refresh()
    }
}