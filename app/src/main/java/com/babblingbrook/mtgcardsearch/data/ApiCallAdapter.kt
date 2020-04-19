package com.babblingbrook.mtgcardsearch.data

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class ApiCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Deferred<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Deferred<ApiResponse<R>> {
        val deferred = CompletableDeferred<ApiResponse<R>>()

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>, throwable: Throwable) {
                try {
                    deferred.complete(ApiResponse.create(throwable))
                } catch (t: Throwable) {
                    deferred.completeExceptionally(t)
                }
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {
                deferred.complete(ApiResponse.create(response))
            }
        })

        return deferred
    }
}