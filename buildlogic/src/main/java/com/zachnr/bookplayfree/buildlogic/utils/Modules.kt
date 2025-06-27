package com.zachnr.bookplayfree.buildlogic.utils

object Modules {
    const val DATA = ":data"
    const val DOMAIN = ":domain"
    const val SHARED = ":shared"

    object Core {
        const val AI_LOCAL = ":core:ailocal"
        const val DATA_STORE = ":core:datastore"
        const val DESIGN_SYSTEM = ":core:designsystem"
        const val FIREBASE = ":core:firebase"
        const val NAVIGATION = ":core:navigation"
        const val NETWORK = ":core:network"
        const val TEST = ":core:test"
        const val UI_COMPONENT = ":core:uicomponent"
        const val UTILS = ":core:utils"
    }

    object Features {
        const val DASHBOARD = ":feature:dashboard"
        const val SPLASH_SCREEN = ":feature:splashscreen"
    }
}