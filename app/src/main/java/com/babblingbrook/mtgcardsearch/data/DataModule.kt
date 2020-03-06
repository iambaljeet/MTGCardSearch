package com.babblingbrook.mtgcardsearch.data

import androidx.room.Room
import com.babblingbrook.mtgcardsearch.MTGCardSearchApp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
            .addConverterFactory(MultipleConverterFactory())
            .baseUrl("https://api.scryfall.com")
            .client(okHttpClient)
            .build()
        return retrofit
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRepository(apiService: ApiService, cardDao: CardDao): Repository {
        return Repository(
            apiService,
            cardDao
        )
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