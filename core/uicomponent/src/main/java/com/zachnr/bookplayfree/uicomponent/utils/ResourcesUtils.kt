package com.zachnr.bookplayfree.uicomponent.utils

import android.content.Context
import com.zachnr.bookplayfree.utils.utils.tryCatchAndReturn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ResourcesUtils : KoinComponent {

    private val context: Context by inject()

    /**
     * Retrieves the corresponding localized string from a dynamic mapping based on the key
     * value of this string.
     *
     * ex: "setting_general".fromDynamicResources()
     *
     * @return The resolved localized string, or an empty string on failure.
     * @throws IllegalArgumentException if the string key is not found in [dynamicTextMap].
     *
     * Note: Only use this when there is a need to use dynamic text like from remote config.
     * If the text is static use composable stringResources() instead.
     * The map is useful to bound the "key" with string id so
     * the text retrieval is fast (avoiding the use of context.resources..getIdentifier())
     */
    fun String?.fromDynamicResources(): String {
        return tryCatchAndReturn("") {
            if (this == null) {
                ""
            } else {
                val stringId = dynamicTextMap[this] ?: throw IllegalArgumentException(
                    "String ID for key \"$this\" not found in dynamicTextMap"
                )
                context.resources.getString(stringId)
            }
        }
    }
}
