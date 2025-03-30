package com.zachnr.bookplayfree.buildlogic.utils

object Modules {
    const val DATA = ":data"
    const val DOMAIN = ":domain"
    const val SHARED = ":shared"

    object Core {
        const val DESIGN_SYSTEM = ":core:designsystem"
        const val NAVIGATION = ":core:navigation"
        const val UI_COMPONENT = ":core:uicomponent"
    }

    object Features {
        const val DASHBOARD = ":feature:dashboard"
        const val SPLASH_SCREEN = ":feature:splashscreen"
    }
}