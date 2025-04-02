package com.zachnr.bookplayfree.network.model

sealed class ResponseWrapper<T> {
    data class Success<T>(val data: T): ResponseWrapper<T>()
    data class Error<T>(
        val code: Int,
        val message: String
    ): ResponseWrapper<T>()
    data class Exception<T>(val throwable: Throwable): ResponseWrapper<T>()
}
