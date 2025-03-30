package com.zachnr.bookplayfree.navigation.route

import kotlinx.serialization.Serializable
interface DashboardDestinations : Destination {
    @Serializable
    data object HomeScreen : Destination

    @Serializable
    data object LibraryScreen : Destination

    @Serializable
    data object SettingScreen : Destination
}
