package com.zachnr.bookplayfree.di

import com.zachnr.bookplayfree.ailocal.di.mlKitTranslatorModule
import com.zachnr.bookplayfree.data.di.getDeepSeekModule
import com.zachnr.bookplayfree.datastore.di.dataStoreModule
import com.zachnr.bookplayfree.domain.di.deepSeekQuoteUseCaseModule
import com.zachnr.bookplayfree.firebase.getFirebaseModule
import com.zachnr.bookplayfree.navigation.di.appNavigationModule
import com.zachnr.bookplayfree.network.di.networkModules
import com.zachnr.bookplayfree.shared.di.sharedModule
import com.zachnr.bookplayfree.utils.di.dispatcherModule
import org.koin.core.context.loadKoinModules

fun loadAppModule() {
    loadKoinModules(
        buildList {
            add(appNavigationModule)
            add(deepSeekQuoteUseCaseModule)
            add(dispatcherModule)
            add(sharedModule)
            add(getFirebaseModule())
            add(dataStoreModule)
            add(mlKitTranslatorModule)
            add(getDeepSeekModule())
            addAll(networkModules)
        }
    )
}
