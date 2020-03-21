package com.babblingbrook.mtgcardsearch.data

import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.Feed
import com.babblingbrook.mtgcardsearch.model.Identifiers
import com.babblingbrook.mtgcardsearch.ui.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    val apiService: ApiService,
    val cardDao: CardDao
) {

    fun search(query: String): Flow<Status<List<Card>>> = flow {
        emit(Status.loading())
        val searchResponse = apiService.search(query)
        if (searchResponse.isSuccessful) {
            val body = searchResponse.body()
            if (body != null) {
                val cardResponse = apiService.getCards(getCardIdentifiers(body.data))
                if (cardResponse.isSuccessful) {
                    val cardResponseBody = cardResponse.body()
                    if (cardResponseBody != null) {
                        emit(Status.success(cardResponseBody.data))
                    }
                } else {
                    emit(Status.error<List<Card>>(cardResponse.message()))
                }
            }
        } else {
            emit(Status.error<List<Card>>(searchResponse.message()))
        }
    }

    private fun getCardIdentifiers(list: List<String>): CardIdentifier {
        val identifierList = list.map { Identifiers(it) }
        return CardIdentifier(identifierList)
    }

    fun getFavorites(): Flow<List<Card>> = cardDao.getAllCards()

    suspend fun addFavorite(card: Card) {
        cardDao.insertCard(card)
    }

    suspend fun removeFavorite(name: String) {
        cardDao.deleteCard(name)
    }

    fun getFeed(url: String): Flow<Status<Feed?>> = flow {
        emit(Status.loading())
        val feedResponse = apiService.getFeeds(url)
        if (feedResponse.isSuccessful) {
            val body = feedResponse.body()
            if (body != null) {
                emit(Status.success(feedResponse.body()))
            }
        } else {
            emit(Status.error<Feed?>(feedResponse.message()))
        }
    }
}