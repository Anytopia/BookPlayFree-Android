package com.zachnr.bookplayfree.domain.di

import com.zachnr.bookplayfree.domain.usecase.GetQuoteDeepSeekUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val deepSeekQuoteUseCaseModule = module {
    factoryOf(::GetQuoteDeepSeekUseCase)
}
