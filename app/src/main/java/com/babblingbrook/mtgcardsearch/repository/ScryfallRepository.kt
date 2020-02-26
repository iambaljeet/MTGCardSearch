package com.babblingbrook.mtgcardsearch.repository

import com.babblingbrook.mtgcardsearch.data.ScryfallApi
import com.babblingbrook.mtgcardsearch.model.Card
import javax.inject.Inject

class ScryfallRepository @Inject constructor(val scryfallApi: ScryfallApi) {

    private suspend fun search(query: String, page: Int) = scryfallApi.search(query, page)

    suspend fun searchCardsWithPagination(query: String, page: Int): List<Card> {
        if (query.isEmpty()) return listOf()
        return search(query, page).data
    }
}