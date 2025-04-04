package com.zachnr.bookplayfree.ailocal.model

sealed class AiLocalWrapper<T> {
    data class Success<T>(val data: T): AiLocalWrapper<T>()
    data class Exception<T>(val throwable: Throwable): AiLocalWrapper<T>()
}
