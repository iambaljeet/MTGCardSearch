package com.babblingbrook.mtgcardsearch.repository

import com.babblingbrook.mtgcardsearch.data.CardDao
import com.babblingbrook.mtgcardsearch.data.Result
import com.babblingbrook.mtgcardsearch.data.ScryfallApi
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.Identifiers
import java.io.IOException
import javax.inject.Inject

class ScryfallRepository @Inject constructor(val scryfallApi: ScryfallApi, val cardDao: CardDao) {

    suspend fun search(query: String): Result<List<Card>> {
        val searchResponse = scryfallApi.search(query)
        if (searchResponse.isSuccessful) {
            val body = searchResponse.body()
            if (body != null && body.isNotEmpty()) {
                return getCards(body)
            }
        }
        return Result.Error(IOException("Card search failed ${searchResponse.code()} ${searchResponse.message()}"))
    }

    suspend fun getCards(list: List<String>) : Result<List<Card>> {
        val response = scryfallApi.getCards(getCardIdentifiers(list))
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.isNotEmpty()) {
                return Result.Success(body)
            }
        }
        return Result.Error(IOException("Fetching cards failed ${response.code()} ${response.message()}"))
    }

    private fun getCardIdentifiers(list: List<String>): CardIdentifier {
        val identifierList = list.map { Identifiers(it) }
        return CardIdentifier(identifierList)
    }
}