package com.zachnr.bookplayfree.initializer

import android.content.Context
import androidx.startup.Initializer

class AppInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        // Not implemented
    }
    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        KoinInitializer::class.java
    )
}
