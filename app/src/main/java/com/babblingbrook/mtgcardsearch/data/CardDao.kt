package com.babblingbrook.mtgcardsearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.babblingbrook.mtgcardsearch.model.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM Card WHERE name = :query")
    suspend fun cardByName(query: String): Card

    @Query("SELECT * FROM Card")
    fun getAllCards(): Flow<List<Card>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Query("DELETE FROM Card WHERE name = :name")
    suspend fun deleteCard(name: String)
}