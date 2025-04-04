package com.zachnr.bookplayfree.di

import com.zachnr.bookplayfree.ailocal.di.mlKitTranslatorModule
import com.zachnr.bookplayfree.navigation.impl.NavigatorImpl
import com.zachnr.bookplayfree.navigation.interfaces.Navigator
import com.zachnr.bookplayfree.navigation.route.Destination
import com.zachnr.bookplayfree.network.di.deepseekClientModule
import com.zachnr.bookplayfree.network.di.mockoonClientModule
import com.zachnr.bookplayfree.network.di.okhttpClientEngineModule
import com.zachnr.bookplayfree.shared.utils.AppConst
import com.zachnr.bookplayfree.shared.utils.DispatcherProvider
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun loadAppModule() {
    val appNavigationModule = module {
        single<Navigator>(named(AppConst.APP_LEVEL_NAVIGATOR)) {
            NavigatorImpl(startDestination = Destination.SplashScreen)
        }
    }
    val utilsModule = module {
        single { DispatcherProvider() }
    }
    val networkModules = listOf(
        deepseekClientModule,
        mockoonClientModule,
        okhttpClientEngineModule
    )
    val aiLocal = listOf(
        mlKitTranslatorModule
    )
    loadKoinModules(
        buildList {
            addAll(networkModules)
            addAll(aiLocal)
            add(appNavigationModule)
            add(utilsModule)
        }
    )
}

