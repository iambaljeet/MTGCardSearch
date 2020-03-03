package com.babblingbrook.mtgcardsearch.repository

import com.babblingbrook.mtgcardsearch.data.CardDao
import com.babblingbrook.mtgcardsearch.data.ScryfallApi
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.Identifiers
import com.babblingbrook.mtgcardsearch.ui.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScryfallRepository @Inject constructor(val scryfallApi: ScryfallApi, val cardDao: CardDao) {

    fun search(query: String): Flow<Status<List<Card>>> = flow {
        emit(Status.loading())
        val searchResponse = scryfallApi.search(query)
        if (searchResponse.isSuccessful) {
            val body = searchResponse.body()
            if (body != null) {
                val cardResponse = scryfallApi.getCards(getCardIdentifiers(body.data))
                if (cardResponse.isSuccessful) {
                    val cardResponseBody = cardResponse.body()
                    if (cardResponseBody != null) {
                        emit(Status.success(cardResponseBody.data))
                    }
                } else {
                    emit(Status.error(cardResponse.message()))
                }
            }
        } else {
            emit(Status.error(searchResponse.message()))
        }
    }

    private fun getCardIdentifiers(list: List<String>): CardIdentifier {
        val identifierList = list.map { Identifiers(it) }
        return CardIdentifier(identifierList)
    }

    fun getFavorites(): Flow<Status<List<Card>>> = flow {
        emit(Status.loading())
        cardDao.getAllCards().collect {
            emit(Status.success(it))
        }
    }

    suspend fun addFavorite(card: Card) {
        cardDao.insertCard(card)
    }

    suspend fun removeFavorite(name: String) {
        cardDao.deleteCard(name)
    }
}