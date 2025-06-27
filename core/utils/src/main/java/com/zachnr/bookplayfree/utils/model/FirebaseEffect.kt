package com.zachnr.bookplayfree.utils.model

interface FirebaseEffect {
    data class OnConfigUpdate(val key: String) : FirebaseEffect
    data class OnConfigError(val message: String?) : FirebaseEffect
}
