package com.babblingbrook.mtgcardsearch.di

import androidx.room.Room
import com.babblingbrook.mtgcardsearch.data.*
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single { OkHttpClient().newBuilder().build() }
    single { Retrofit.Builder()
        .addConverterFactory(MultipleConverterFactory())
        .baseUrl("https://api.scryfall.com")
        .client(get())
        .build() }
    single { get<Retrofit>().create(ApiService::class.java) }
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "Scryfall.db").build() }
    single { get<AppDatabase>().cardDao() }
    single { Repository(get(), get()) }
}