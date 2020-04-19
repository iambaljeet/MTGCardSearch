package com.babblingbrook.mtgcardsearch.data

import androidx.room.*
import com.babblingbrook.mtgcardsearch.model.FeedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {
    @Query("SELECT * FROM FeedItem")
    suspend fun loadFeedItems(): List<FeedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedItem(feedItem: FeedItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedItems(feedItems: List<FeedItem>)

    @Query("DELETE FROM FeedItem")
    fun deleteAllFeedItems()

    @Transaction
    suspend fun deleteAndReplaceFeedItems(feedItems: List<FeedItem>) {
        deleteAllFeedItems()
        insertFeedItems(feedItems)
    }
}