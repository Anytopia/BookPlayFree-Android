package com.zachnr.bookplayfree.navigation.di

import com.zachnr.bookplayfree.navigation.impl.NavigatorImpl
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.route.Destination
import com.zachnr.bookplayfree.utils.utils.AppConst
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appNavigationModule = module {
    single<Navigator>(named(AppConst.APP_LEVEL_NAVIGATOR)) {
        NavigatorImpl(startDestination = Destination.SplashScreen)
    }
}
