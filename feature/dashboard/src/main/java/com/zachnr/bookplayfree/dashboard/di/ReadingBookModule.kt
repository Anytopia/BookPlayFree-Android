package com.zachnr.bookplayfree.dashboard.di

import com.zachnr.bookplayfree.dashboard.presentation.pages.readingbook.ReadingBookViewModel
import com.zachnr.bookplayfree.utils.utils.NavConst
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun loadReadingBookKoinInject() {
    loadKoinModules(
        listOf(
            vmModule
        )
    )
}

private val vmModule = module {
    viewModel { ReadingBookViewModel(get(named(NavConst.DASHBOARD_LEVEL_NAVIGATOR))) }
}