package com.zachnr.bookplayfree.utils.utils

import android.util.Log

/**
 * Executes the given block and returns its result.
 * If an exception occurs, logs the error and returns the specified fallback value.
 *
 * @param onErrorReturn The value to return if an exception is thrown.
 * @param block The lambda to execute.
 * @return The result of the block, or `onErrorReturn` if an exception occurs.
 */
// TODO: Assign e.message to onErrorReturn
inline fun <T> tryCatchAndReturn(
    onErrorReturn: T,
    block: () -> T
): T {
    return try {
        block()
    } catch (e: Exception) {
        Log.e("tryCatchAndReturn", "Error occurred", e)
        onErrorReturn
    }
}