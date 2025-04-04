package com.zachnr.bookplayfree.ailocal.di

import com.zachnr.bookplayfree.ailocal.OfflineTranslator
import com.zachnr.bookplayfree.ailocal.mlkit.MLKitTranslator
import org.koin.dsl.module

val mlKitTranslatorModule = module {
    single<OfflineTranslator> {
        MLKitTranslator()
    }
}
