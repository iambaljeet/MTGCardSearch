package com.babblingbrook.mtgcardsearch.data

import com.babblingbrook.mtgcardsearch.model.CardData
import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.SearchData
import retrofit2.Response
import retrofit2.http.*

interface ScryfallApi {
    @Headers("Content-Type: application/json")
    @POST("/cards/collection")
    suspend fun getCards(@Body identifier: CardIdentifier): Response<CardData>


    @GET("/cards/autocomplete")
    suspend fun search(
        @Query("q") query: String?
    ): Response<SearchData>
}
