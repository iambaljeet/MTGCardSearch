package com.babblingbrook.mtgcardsearch.ui

sealed class Status<out T> {

    data class Success<out T>(
        val data: T
    ) : Status<T>()

    class Loading<out T>(
        val data: T?
    ) : Status<T>()

    data class NoNetwork<out T>(
        val data: T?
    ) : Status<T>()

    data class Error<out T>(
        val message: String,
        val data: T?
    ) : Status<T>()

    companion object {
        fun <T> success(data: T): Status<T> = Success(data)

        fun <T> loading(data: T?): Status<T> = Loading(data)

        fun <T> noNetwork(data: T?): Status<T> = NoNetwork(data)

        fun <T> error(message: String, data: T?): Status<T> = Error(message, data)
    }
}