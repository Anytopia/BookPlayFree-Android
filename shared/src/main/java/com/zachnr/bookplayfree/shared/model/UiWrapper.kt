package com.zachnr.bookplayfree.shared.model

sealed interface UiWrapper<out T> {
    data class Success<out T>(val data: T): UiWrapper<T>
    data object Empty: UiWrapper<Nothing>
}
