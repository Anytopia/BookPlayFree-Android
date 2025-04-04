package com.zachnr.bookplayfree.dashboard.di

import com.zachnr.bookplayfree.dashboard.presentation.DashboardViewModel
import com.zachnr.bookplayfree.utils.utils.AppConst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun loadDashboardModule() {
    loadKoinModules(
        listOf(
            dashboardVMModule
        )
    )
}

private val dashboardVMModule = module {
    viewModel { DashboardViewModel(get(named(AppConst.APP_LEVEL_NAVIGATOR))) }
}
