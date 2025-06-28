package com.zachnr.bookplayfree.dashboard.di

import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.SettingViewModel
import com.zachnr.bookplayfree.dashboard.presentation.pages.setting.mapper.SettingMapper
import com.zachnr.bookplayfree.data.di.getFirebaseRC
import com.zachnr.bookplayfree.data.di.loadSettingRepoModule
import com.zachnr.bookplayfree.utils.utils.AppConst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun loadSettingKoinInject() {
    val settingVMModule = module {
        viewModel {
            SettingViewModel(
                get(named(AppConst.APP_LEVEL_NAVIGATOR)),
                get(),
                get(),
                get()
            )
        }
    }
    val settingHelperModule = module {
        factoryOf(::SettingMapper)
    }
    loadKoinModules(
        listOf(
            settingVMModule,
            settingHelperModule,
            getFirebaseRC(),
            loadSettingRepoModule()
        )
    )
}
