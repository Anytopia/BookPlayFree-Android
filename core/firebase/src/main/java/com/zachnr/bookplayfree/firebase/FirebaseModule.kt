package com.zachnr.bookplayfree.firebase

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadFirebaseModule() : Module = module {
    single<FirebaseRemoteConfig> {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            fetchAndActivate()
        }
    }
}

fun loadFirebaseRemoteConfig() : Module = module {
    factory<RemoteConfigDataSource> {
        RemoteConfigDataSourceImpl(get(), get())
    }
}
