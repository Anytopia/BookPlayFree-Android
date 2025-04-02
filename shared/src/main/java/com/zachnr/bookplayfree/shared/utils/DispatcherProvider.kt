package com.zachnr.bookplayfree.shared.utils

import kotlinx.coroutines.Dispatchers

private interface CoroutineDispatchers {
    val main
        get() = Dispatchers.Main
    val default
        get() = Dispatchers.Default
    val io
        get() = Dispatchers.IO
    val unconfined
        get() = Dispatchers.Unconfined
}

class DispatcherProvider: CoroutineDispatchers

