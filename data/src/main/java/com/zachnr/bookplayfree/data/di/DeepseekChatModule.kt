package com.zachnr.bookplayfree.data.di

import com.zachnr.bookplayfree.data.mapper.DeepSeekDataMapper
import com.zachnr.bookplayfree.data.repository.DeepSeekRepositoryImpl
import com.zachnr.bookplayfree.domain.repository.DeepSeekRepository
import com.zachnr.bookplayfree.shared.utils.NetworkConst.DEEPSEEK_CLIENT_QUALIFIER
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun getDeepSeekModule(): Module {
    return module {
        factory<DeepSeekRepository> {
            DeepSeekRepositoryImpl(
                get(named(DEEPSEEK_CLIENT_QUALIFIER)),
                get()
            )
        }
        factoryOf(::DeepSeekDataMapper)
    }
}
