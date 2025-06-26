package com.zachnr.bookplayfree.navigation.route

import android.net.Uri
import androidx.core.net.toUri
import kotlinx.serialization.Serializable

interface DashboardDestinations : Destination {
    @Serializable
    data object HomeScreen : Destination

    @Serializable
    data object LibraryScreen : Destination

    @Serializable
    data class ReadingBookScreen(
        val uri: String
    ) : Destination {
        fun getUri(): Uri {
            return uri.toUri()
        }
    }

    @Serializable
    data object SettingScreen : Destination
}
