package com.zachnr.bookplayfree.datastore

import com.zachnr.bookplayfree.datastore.model.UserDataPref
import kotlinx.coroutines.flow.Flow

/**
 * Interface for accessing and modifying user preferences stored in DataStore.
 */
interface DataStoreSource {

    /**
     * A Flow that emits the latest [UserDataPref] whenever any of its values change.
     * This can be used to observe user preferences reactively.
     */
    val userPreference: Flow<UserDataPref>

    /**
     * Updates the target language for translation.
     *
     * @param langCode The ISO 639-1 language code (e.g., "en", "es", "id").
     */
    suspend fun setLangTranslationTarget(langCode: String)

    /**
     * Sets whether the 3D effect for certain UI elements is enabled.
     *
     * @param isEnabled True to enable the 3D effect, false to disable.
     */
    suspend fun setEffect3DEnabled(isEnabled: Boolean)

    /**
     * Sets whether the "shake to next" feature is enabled.
     *
     * @param isEnabled True to enable shake to next, false to disable.
     */
    suspend fun setShakeToNextEnabled(isEnabled: Boolean)

    /**
     * Sets whether content should be read aloud when launched/opened.
     *
     * @param isEnabled True to enable read on launch, false to disable.
     */
    suspend fun setReadWhenLaunchEnabled(isEnabled: Boolean)

    /**
     * Sets whether the dashboard coach mark should be shown to the user.
     * This is typically set to false after the user has seen it once.
     *
     * @param isShown True if the coach mark should be shown, false otherwise.
     */
    suspend fun setShowDashboardCoachMark(isShown: Boolean)

    /**
     * Sets whether the reading screen coach mark should be shown to the user.
     * This is typically set to false after the user has seen it once.
     *
     * @param isShown True if the coach mark should be shown, false otherwise.
     */
    suspend fun setShowReadingCoachMark(isShown: Boolean)

    /**
     * Updates the user's reading time goal.
     *
     * @param minutes The reading time goal in minutes.
     */
    suspend fun setUserGoalReadingTime(minutes: Int)

    /**
     * Updates the user's page reading goal.
     *
     * @param pages The number of pages for the goal.
     */
    suspend fun setUserGoalPages(pages: Int)

    /**
     * Updates the user's book reading goal.
     *
     * @param books The number of books for the goal.
     */
    suspend fun setUserGoalBooks(books: Int)

    /**
     * Clears all user preferences to their default values.
     * Useful for logout or reset functionality.
     */
    suspend fun clearUserPreferences()
}
