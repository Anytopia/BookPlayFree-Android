package com.zachnr.bookplayfree.firebase

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.zachnr.bookplayfree.utils.utils.FirebaseConst.MINIMUM_FETCH_INTERVAL
import org.koin.core.module.Module
import org.koin.dsl.module

fun getFirebaseModule() : Module = module {
    single<FirebaseRemoteConfig> {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = MINIMUM_FETCH_INTERVAL
        }
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            fetchAndActivate()
        }
    }
}

fun getFirebaseRemoteConfig() : Module = module {
    factory<RemoteConfigDataSource> {
        RemoteConfigDataSourceImpl(get(), get())
    }
}
