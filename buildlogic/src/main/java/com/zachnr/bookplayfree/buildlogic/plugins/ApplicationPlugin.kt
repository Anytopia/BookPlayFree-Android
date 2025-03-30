package com.zachnr.bookplayfree.buildlogic.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.zachnr.bookplayfree.buildlogic.ext.configureKotlinAndroid
import com.zachnr.bookplayfree.buildlogic.utils.AppConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureBuildType()
                with(defaultConfig) {
                    targetSdk = AppConfig.TARGET_SDK
                    applicationId = AppConfig.APP_ID
                    versionCode = AppConfig.VERSION_CODE
                    versionName = AppConfig.VERSION_NAME
                }
            }
        }
    }

    private fun ApplicationExtension.configureBuildType() {
        buildTypes {
            // Configure the build type here
            debug {

            }

            release {

            }
        }
    }
}
