package com.zachnr.bookplayfree.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreSource {
    val userPreference: Flow<UserPreference>

    /**
     * Save preferred target translation language code to datastore
     *
     * @param langCode: The target language code. (ex: "en")
     */
    suspend fun setLangTranslationTarget(langCode: String)
}
