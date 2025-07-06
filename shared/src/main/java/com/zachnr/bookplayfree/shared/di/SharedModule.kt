package com.zachnr.bookplayfree.shared.di

import com.zachnr.bookplayfree.shared.viewmodel.MainActivitySharedVM
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::MainActivitySharedVM)
}
