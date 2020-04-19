package com.babblingbrook.mtgcardsearch.data

import androidx.lifecycle.LiveData
import com.babblingbrook.mtgcardsearch.model.*
import com.babblingbrook.mtgcardsearch.ui.Status
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val apiService: ApiService,
    private val cardDao: CardDao,
    private val feedDao: FeedDao
) {

    fun search(query: String): Flow<Status<List<Card>>> = flow {
        emit(Status.loading(null))
        when (val searchResponse = apiService.search(query).await()) {
            is ApiSuccessResponse -> {
                when (val cardResponse = apiService.getCards(getCardIdentifiers(searchResponse.body.data)).await()) {
                    is ApiSuccessResponse -> {
                        val cardResponseBody = cardResponse.body
                        emit(Status.success(cardResponseBody.data))
                    }
                    is ApiNetworkError -> {
                        emit(Status.noNetwork(null))
                    }
                    is ApiGenericError -> {
                        emit(Status.error(cardResponse.errorMessage, null))
                    }
                }
            }
            is ApiNetworkError -> {
                emit(Status.NoNetwork(null))
            }
            is ApiGenericError -> {
                emit(Status.Error(searchResponse.errorMessage, null))
            }
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

    suspend fun getFeed(): LiveData<Status<List<FeedItem>>> {
        return object : NetworkBoundResource<List<FeedItem>, Feed>() {
            override fun shouldFetch(data: List<FeedItem>?): Boolean {
                return true
            }
            override suspend fun loadFromDb(): List<FeedItem> {
                return feedDao.loadFeedItems()
            }
            override suspend fun createCallAsync(): Deferred<ApiResponse<Feed>> {
                return apiService.getFeedsAsync("https://magic.wizards.com/en/rss/rss.xml")
            }
            override suspend fun saveCallResults(feed: Feed) {
                feedDao.deleteAndReplaceFeedItems(feed.channel.item)
            }
        }.build().asLiveData()
    }
}