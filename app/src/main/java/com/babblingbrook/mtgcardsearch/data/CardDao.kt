package com.babblingbrook.mtgcardsearch.data

import androidx.room.*
import com.babblingbrook.mtgcardsearch.model.Card

@Dao
interface CardDao {
    @Query("SELECT * FROM Card WHERE name = :query")
    suspend fun cardByName(query: String) : Card

    @Query("SELECT * FROM Card")
    suspend fun getAllCards(): List<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Card)

    @Query("DELETE FROM Card WHERE name = :name")
    suspend fun deleteCard(name: String)
}