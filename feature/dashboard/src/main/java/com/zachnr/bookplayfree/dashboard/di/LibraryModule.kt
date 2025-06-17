package com.zachnr.bookplayfree.dashboard.di

import com.zachnr.bookplayfree.dashboard.presentation.pages.library.LibraryViewModel
import com.zachnr.bookplayfree.data.repository.BookRepositoryImpl
import com.zachnr.bookplayfree.domain.repository.BookRepository
import com.zachnr.bookplayfree.utils.utils.AppConst
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun loadLibraryKoinInject() {
    val libraryVMModule = module {
        viewModel {
            LibraryViewModel(
                get(named(AppConst.APP_LEVEL_NAVIGATOR)),
                get(),
                get()
            )
        }
    }
    val libraryRepoModule = module {
        factory<BookRepository> {
            BookRepositoryImpl(
                get(),
                androidContext()
            )
        }
    }
    loadKoinModules(
        listOf(
            libraryVMModule,
            libraryRepoModule
        )
    )
}
