package com.babblingbrook.mtgcardsearch.data

import androidx.room.Room
import com.babblingbrook.mtgcardsearch.MTGCardSearchApp
import com.babblingbrook.mtgcardsearch.repository.ScryfallRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.scryfall.com")
            .client(okHttpClient)
            .build()
        return retrofit
    }

    @Provides
    fun provideApi(retrofit: Retrofit): ScryfallApi {
        return retrofit.create(ScryfallApi::class.java)
    }

    @Provides
    fun provideRepository(scryfallApi: ScryfallApi, cardDao: CardDao): ScryfallRepository {
        return ScryfallRepository(scryfallApi, cardDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: MTGCardSearchApp): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "Scryfall.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): CardDao {
        return appDatabase.cardDao()
    }
}