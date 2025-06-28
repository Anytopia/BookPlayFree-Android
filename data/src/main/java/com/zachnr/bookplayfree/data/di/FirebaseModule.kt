package com.zachnr.bookplayfree.data.di

import com.zachnr.bookplayfree.firebase.getFirebaseRemoteConfig
import org.koin.core.module.Module

fun getFirebaseRC() : Module = getFirebaseRemoteConfig()