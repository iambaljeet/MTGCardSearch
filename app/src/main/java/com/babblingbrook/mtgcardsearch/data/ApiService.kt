package com.babblingbrook.mtgcardsearch.data

import com.babblingbrook.mtgcardsearch.model.CardIdentifier
import com.babblingbrook.mtgcardsearch.model.Feed
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/cards/collection")
    @JsonResponse
    fun getCards(@Body cardIdentifier: CardIdentifier): Deferred<ApiResponse<CardData>>

    @GET("/cards/autocomplete")
    @JsonResponse
    fun search(
        @Query("q") query: String?
    ): Deferred<ApiResponse<SearchData>>

    @GET
    @XmlResponse
    fun getFeedsAsync(@Url url: String): Deferred<ApiResponse<Feed>>
}
