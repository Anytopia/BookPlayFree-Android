package com.zachnr.bookplayfree.data.di

import com.zachnr.bookplayfree.data.repository.setting.SettingRepositoryImpl
import com.zachnr.bookplayfree.domain.repository.setting.SettingRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun loadSettingRepoModule(): Module = module {
    factory<SettingRepository> {
        SettingRepositoryImpl(get())
    }
}
