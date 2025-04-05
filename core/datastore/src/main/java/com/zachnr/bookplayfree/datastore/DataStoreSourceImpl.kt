package com.zachnr.bookplayfree.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow

class DataStoreSourceImpl(
    private val dataStore: DataStore<UserPreference>
) : DataStoreSource {

    override val userPreference: Flow<UserPreference> = dataStore.data

    override suspend fun setLangTranslationTarget(langCode: String) {
        dataStore.updateData {
            it.toBuilder()
                .setReadingPreferences(
                    it.readingPreferences.toBuilder()
                        .setTargetLangTranslation(langCode)
                        .build()
                )
                .build()
        }
    }
}
