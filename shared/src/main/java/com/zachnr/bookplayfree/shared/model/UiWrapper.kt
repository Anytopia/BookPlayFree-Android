package com.zachnr.bookplayfree.shared.model

/**
 * Represents the general UI state of data, commonly used to wrap the result of asynchronous operations
 * such as network requests or database fetches.
 *
 * This sealed interface provides a clear and type-safe way to handle different states of UI data:
 *
 * - [Success]: Indicates that the data was successfully retrieved.
 * - [Empty]: Represents a successful response but with no data (e.g., empty list or null response).
 * - [Loading]: Indicates that the data is currently being loaded.
 *
 * @param T The type of data being wrapped.
 */
@Deprecated(
    message = "Don't use this wrapper for UI. Use dedicated screen state instead. See SettingState"
)
// TODO: Remove this interface
sealed interface UiWrapper<out T> {

    /**
     * Represents a successful state with non-null [data].
     *
     * @param T The type of the data.
     * @property data The actual data returned from a successful operation.
     */
    data class Success<out T>(val data: T): UiWrapper<T>

    /**
     * Represents a successful operation with no meaningful data to return.
     * Commonly used when a list is empty or no content is expected.
     */
    data object Empty: UiWrapper<Nothing>

    /**
     * Represents the loading state while data is being fetched.
     * Typically shown while waiting for an operation to complete.
     */
    data object Loading: UiWrapper<Nothing>
}
