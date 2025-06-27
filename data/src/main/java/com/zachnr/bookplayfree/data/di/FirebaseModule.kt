package com.zachnr.bookplayfree.data.di

import com.zachnr.bookplayfree.firebase.loadFirebaseRemoteConfig
import org.koin.core.module.Module

fun loadFirebaseRC() : Module = loadFirebaseRemoteConfig()