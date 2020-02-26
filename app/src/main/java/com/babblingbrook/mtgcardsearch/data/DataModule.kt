package com.babblingbrook.mtgcardsearch.data

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
    fun provideRepository(scryfallApi: ScryfallApi): ScryfallRepository {
        return ScryfallRepository(scryfallApi)
    }
}