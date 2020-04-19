package com.babblingbrook.mtgcardsearch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.FeedItem

@Database(entities = [Card::class, FeedItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun feedDao(): FeedDao
}