package com.zachnr.bookplayfree.buildlogic.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.zachnr.bookplayfree.buildlogic.ext.configureDetekt
import com.zachnr.bookplayfree.buildlogic.ext.configureKotlinAndroid
import com.zachnr.bookplayfree.buildlogic.ext.configureUnitTest
import com.zachnr.bookplayfree.buildlogic.ext.libs
import com.zachnr.bookplayfree.buildlogic.utils.AppConfig
import com.zachnr.bookplayfree.buildlogic.utils.Modules
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "io.gitlab.arturbosch.detekt")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureBuildType()
                configureUnitTest()

                with(defaultConfig) {
                    targetSdk = AppConfig.TARGET_SDK
                    applicationId = AppConfig.APP_ID
                    versionCode = AppConfig.VERSION_CODE
                    versionName = AppConfig.VERSION_NAME
                }
            }
            configureDetekt()
            configureAppDependencies()
        }
    }

    private fun Project.configureAppDependencies() {
        val impl = "implementation"
        val debugImpl = "debugImplementation"

        dependencies {
            add(impl, project(Modules.SHARED))
            add(impl, project(Modules.DATA))
            add(impl, project(Modules.DOMAIN))
            add(impl, project(Modules.Core.AI_LOCAL))
            add(impl, project(Modules.Core.DESIGN_SYSTEM))
            add(impl, project(Modules.Core.NAVIGATION))
            add(impl, project(Modules.Core.NETWORK))
            add(impl, project(Modules.Core.UTILS))
            add(impl, project(Modules.Core.TEST))
            add(impl, project(Modules.Features.DASHBOARD))
            add(impl, project(Modules.Features.SPLASH_SCREEN))

            add(impl, libs.findLibrary("androidx.activity").get())
            add(impl, libs.findLibrary("androidx.activity.compose").get())
            add(impl, libs.findLibrary("androidx.core.ktx").get())
            add(impl, libs.findLibrary("androidx.lifecycle").get())
            add(impl, libs.findLibrary("androidx.material3").get())
            add(impl, libs.findLibrary("androidx.navigation.compose").get())
            add(impl, libs.findLibrary("androidx.startup.runtime").get())

            // Firebase
            add(impl, platform(libs.findLibrary("firebase.bom").get()))
            add(impl, libs.findLibrary("firebase.crashlytics").get())

            // Koin
            add(impl, libs.findLibrary("koin.core").get())
            add(impl, libs.findLibrary("koin.android").get())
            add(impl, libs.findLibrary("koin.androidx.compose").get())

            // Leakcanary
            add(debugImpl, libs.findLibrary("leakcanary.android").get())

        }
    }


    private fun ApplicationExtension.configureBuildType() {
        buildTypes {
            // Configure the build type here
            debug {

            }

            release {
                isMinifyEnabled = true
                isShrinkResources = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}
