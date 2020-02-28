package com.babblingbrook.mtgcardsearch.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.babblingbrook.mtgcardsearch.model.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM Card WHERE name = :query")
    fun cardByName(query: String) : LiveData<Card>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)
}