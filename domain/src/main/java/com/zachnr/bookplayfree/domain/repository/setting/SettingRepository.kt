package com.zachnr.bookplayfree.domain.repository.setting

import com.zachnr.bookplayfree.domain.model.DomainWrapper
import com.zachnr.bookplayfree.domain.model.setting.SettingOrderingDomain
import com.zachnr.bookplayfree.utils.model.FirebaseEffect
import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    /**
     * Returns a [Flow] emitting [FirebaseEffect] representing the state of Remote Config updates.
     *
     * This flow emits events whenever Remote Config parameters are updated in real-time,
     * enabling the app to react accordingly, such as activating new values or updating UI.
     *
     * @return a flow of [FirebaseEffect] reflecting Remote Config update states.
     */
    val firebaseRcEffect: Flow<FirebaseEffect>

    /**
     * Initializes and registers a listener for Remote Config update events.
     *
     * This method starts listening for real-time Remote Config changes from the Firebase backend.
     * When a new Remote Config version is published, the listener fetches the updated values
     * and triggers the update flow.
     *
     * Typically called once during app startup or when Remote Config updates are needed.
     */
    suspend fun initRCConfigUpdateListener()

    /**
     * Removes the Remote Config update listener and stops receiving real-time updates.
     *
     * This method unregisters the listener registered by [initRCConfigUpdateListener],
     * closing the connection to the Remote Config backend if no other listeners exist.
     *
     * Call this to clean up resources or when real-time updates are no longer required.
     */
    suspend fun removeRCConfigUpdateListener()

    /**
     * Handle get ordering setting from remote config or local json
     *
     * @return [DomainWrapper<SettingOrderingDomain>]
     */
    suspend fun getSettingMenuOrdering(): DomainWrapper<List<SettingOrderingDomain>>
}
