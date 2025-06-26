package com.zachnr.bookplayfree.firebase

import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

/**
 * Interface to retrieve and observe remote configuration data.
 * Provides methods to fetch config values and listen for updates in real-time.
 */
interface RemoteConfigRepository {

    /**
     * A [Flow] emitting the current state of remote config effects.
     * This stream can be observed to react to changes or errors related to remote config
     * operations.
     */
    val effect: Flow<FirebaseEffect>

    /**
     * Retrieves a remote config object associated with the given [key], deserializes it using
     * the provided [serializer],and returns the default value [default] if the key is not present
     * or deserialization fails. Also listens for updates to the config and updates the returned
     * value accordingly.
     *
     * @param key The remote config key to fetch.
     * @param serializer The Kotlinx serialization [KSerializer] for the expected type [T].
     * @param default The default value to return if no config is found or deserialization fails.
     * @return The current value of the config object of type [T].
     */
    suspend fun <T> getObject(
        key: String,
        serializer: KSerializer<T>,
        default: T
    ): T

    /**
     * Retrieves a boolean remote config value for the specified [key].
     * Listens for updates and reflects changes in the returned value.
     *
     * @param key The remote config key to fetch.
     * @return The current boolean value associated with the key.
     */
    suspend fun getBoolean(
        key: String,
    ): Boolean

    /**
     * Registers a listener to receive remote config update events.
     * This enables the repository to react to config changes pushed from the remote server.
     * Should be called to start listening for config updates.
     */
    suspend fun registerConfigUpdateListener()

    /**
     * Unregisters the remote config update listener.
     * Stops the repository from receiving further config update events.
     * Should be called to clean up resources when updates are no longer needed.
     */
    suspend fun unregisterConfigUpdateListener()
}
