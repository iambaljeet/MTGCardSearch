package com.babblingbrook.mtgcardsearch.data

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        check(returnType is ParameterizedType) { "$returnType must be parameterized. Raw types are not supported" }

        val observableType = getParameterUpperBound(0, returnType)
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        val bodyType = getParameterUpperBound(0, observableType)

        return ApiCallAdapter<Any>(bodyType)
    }
}