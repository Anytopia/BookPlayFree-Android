package com.zachnr.bookplayfree.utils.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

private interface CoroutineDispatchers {
    val main: CoroutineDispatcher get() = Dispatchers.Main
    val default: CoroutineDispatcher get() = Dispatchers.Default
    val io: CoroutineDispatcher get() = Dispatchers.IO
    val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
}

open class DispatcherProvider : CoroutineDispatchers {
    companion object {
        fun test(
            dispatchers: CoroutineDispatcher
        ): DispatcherProvider = object : DispatcherProvider() {
            override val main = dispatchers
            override val default = dispatchers
            override val io = dispatchers
            override val unconfined = dispatchers
        }
    }
}
