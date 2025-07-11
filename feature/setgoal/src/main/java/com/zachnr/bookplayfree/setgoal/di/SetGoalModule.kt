package com.zachnr.bookplayfree.setgoal.di

import com.zachnr.bookplayfree.setgoal.presentation.SetGoalViewModel
import com.zachnr.bookplayfree.utils.utils.AppConst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun loadSetGoalModule() {
    loadKoinModules(
        listOf(setGoalVMModule)
    )
}

private val setGoalVMModule = module {
    viewModel { SetGoalViewModel(get(named(AppConst.APP_LEVEL_NAVIGATOR))) }
}
