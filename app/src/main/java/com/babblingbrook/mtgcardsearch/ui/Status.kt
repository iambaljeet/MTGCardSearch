package com.babblingbrook.mtgcardsearch.ui

sealed class Status<ResultType> {

    data class Success<ResultType>(
        val data: ResultType
    ) : Status<ResultType>()

    class Loading<ResultType> : Status<ResultType>()

    data class Error<ResultType>(
        val message: String
    ) : Status<ResultType>()

    companion object {
        fun <ResultType> success(data: ResultType): Status<ResultType> = Success(data)

        fun <ResultType> loading(): Status<ResultType> = Loading()

        fun <ResultType> error(message: String): Status<ResultType> = Error(message)
    }
}