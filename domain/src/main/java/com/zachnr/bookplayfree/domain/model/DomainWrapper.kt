package com.zachnr.bookplayfree.domain.model

sealed class DomainWrapper<T> {
    data class Success<T>(val data: T): DomainWrapper<T>()
    data class Error<T>(
        val code: Int? = 0,
        val message: String = ""
    ): DomainWrapper<T>()
}
