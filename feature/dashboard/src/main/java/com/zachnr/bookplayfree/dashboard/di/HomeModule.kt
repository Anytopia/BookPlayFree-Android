package com.zachnr.bookplayfree.dashboard.di

import com.zachnr.bookplayfree.dashboard.presentation.pages.home.HomeViewModel
import com.zachnr.bookplayfree.data.di.getDeepSeekModule
import com.zachnr.bookplayfree.domain.usecase.GetQuoteDeepSeekUseCase
import com.zachnr.bookplayfree.utils.utils.NavConst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun loadHomeKoinInject() {
    val homeVMModule = module {
        viewModel {
            HomeViewModel(
                get(named(NavConst.DASHBOARD_LEVEL_NAVIGATOR))
            )
        }
    }
    val homeUseCaseModule = module {
        factoryOf(::GetQuoteDeepSeekUseCase)
    }
    loadKoinModules(
        listOf(
            homeVMModule,
            homeUseCaseModule,
            getDeepSeekModule()
        )
    )
}
