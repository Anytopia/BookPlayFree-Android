package com.zachnr.bookplayfree.initializer

import android.content.Context
import androidx.startup.Initializer
import com.zachnr.bookplayfree.di.loadAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context.applicationContext)
        }
        loadAppModule()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
