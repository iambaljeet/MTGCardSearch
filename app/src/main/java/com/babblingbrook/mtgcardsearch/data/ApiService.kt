package com.babblingbrook.mtgcardsearch.data

import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.Feed
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/cards/collection")
    @JsonResponse
    suspend fun getCards(@Body cardIdentifier: CardIdentifier): Response<CardData>

    @GET("/cards/autocomplete")
    @JsonResponse
    suspend fun search(
        @Query("q") query: String?
    ): Response<SearchData>

    @GET
    @XmlResponse
    suspend fun getFeeds(@Url url: String): Response<Feed>
}
