package com.zachnr.bookplayfree.datastore.di

import androidx.datastore.core.DataStoreFactory
import com.zachnr.bookplayfree.datastore.DataStoreSource
import com.zachnr.bookplayfree.datastore.DataStoreSourceImpl
import com.zachnr.bookplayfree.datastore.ext.dataStoreFile
import com.zachnr.bookplayfree.datastore.utils.UserPreferenceSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataStoreModule = module {
    single {
        DataStoreFactory.create(
            serializer = UserPreferenceSerializer,
            scope = CoroutineScope(Dispatchers.IO),
        ) {
            androidApplication().dataStoreFile("user_preferences.pb")
        }
    }
    singleOf(::DataStoreSourceImpl) bind DataStoreSource::class
}
