package com.babblingbrook.mtgcardsearch.data

import com.babblingbrook.mtgcardsearch.model.Card
import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import retrofit2.Response
import retrofit2.http.*

interface ScryfallApi {
    @Headers("Content-Type: application/json")
    @POST("/cards/collection")
    suspend fun getCards(@Body identifier: CardIdentifier): Response<List<Card>>


    @GET("/cards/autocomplete")
    suspend fun search(
        @Query("q") query: String?
    ): Response<List<String>>
}
