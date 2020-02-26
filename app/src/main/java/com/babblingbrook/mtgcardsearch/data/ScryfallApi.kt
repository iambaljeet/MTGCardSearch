package com.babblingbrook.mtgcardsearch.data

import com.babblingbrook.mtgcardsearch.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ScryfallApi {
    @GET("/cards/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int
    ): NetworkResponse
}