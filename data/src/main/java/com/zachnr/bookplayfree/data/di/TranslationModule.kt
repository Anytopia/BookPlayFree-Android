package com.zachnr.bookplayfree.data.di

import com.zachnr.bookplayfree.data.mapper.TranslationMapper
import com.zachnr.bookplayfree.data.repository.TranslationRepositoryImpl
import com.zachnr.bookplayfree.domain.repository.TranslationRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun getTranslationModule(): Module {
    return module {
        factoryOf(::TranslationMapper)
        factory<TranslationRepository> {
            TranslationRepositoryImpl(
                get(),
                get()
            )
        }
    }
}
