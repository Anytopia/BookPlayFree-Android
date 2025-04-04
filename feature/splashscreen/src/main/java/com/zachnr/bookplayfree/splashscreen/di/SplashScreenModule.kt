package com.zachnr.bookplayfree.splashscreen.di

import com.zachnr.bookplayfree.splashscreen.presentation.SplashScreenViewModel
import com.zachnr.bookplayfree.utils.utils.AppConst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun loadSplashscreenModule() {
    loadKoinModules(
        listOf(splashScreenVMModule)
    )
}

private val splashScreenVMModule = module {
    viewModel {
        SplashScreenViewModel(get(named(AppConst.APP_LEVEL_NAVIGATOR))
        )
    }
}
