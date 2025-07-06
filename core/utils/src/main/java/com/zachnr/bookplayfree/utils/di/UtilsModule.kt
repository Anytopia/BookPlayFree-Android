package com.zachnr.bookplayfree.utils.di

import com.zachnr.bookplayfree.utils.utils.DispatcherProvider
import org.koin.dsl.module

val dispatcherModule = module {
    single { DispatcherProvider() }
}
