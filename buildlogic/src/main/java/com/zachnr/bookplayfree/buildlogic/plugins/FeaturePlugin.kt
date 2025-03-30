package com.zachnr.bookplayfree.buildlogic.plugins

import com.android.build.gradle.LibraryExtension
import com.zachnr.bookplayfree.buildlogic.ext.libs
import com.zachnr.bookplayfree.buildlogic.utils.Modules
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class FeaturePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureFeaturePlugins()
            configureBuildFeatures()
            configureFeatureDependencies()
        }
    }

    private fun Project.configureFeaturePlugins() {
        apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
        apply(plugin = "com.zachnr.bookplayfree.library")
        apply(plugin = "com.zachnr.bookplayfree.library.compose")
    }

    private fun Project.configureBuildFeatures() {
        extensions.configure<LibraryExtension> {
            buildFeatures {
                dataBinding = true
                viewBinding = true
            }
        }
    }

    private fun Project.configureFeatureDependencies() {
        val implementationConfigName = "implementation"
        dependencies {
            // Modules
            add(implementationConfigName, project(Modules.Core.UI_COMPONENT))
            add(implementationConfigName, project(Modules.Core.NAVIGATION))
            add(implementationConfigName, project(Modules.Core.DESIGN_SYSTEM))
            add(implementationConfigName, project(Modules.SHARED))

            // External libraries
            add(implementationConfigName, libs.findLibrary("androidx-core-ktx").get())
            add(implementationConfigName, libs.findLibrary("androidx-appcompat").get())
            add(implementationConfigName, libs.findLibrary("androidx-material3-android").get())
            add(implementationConfigName, libs.findLibrary("koin-core").get())
            add(implementationConfigName, libs.findLibrary("koin-android").get())
            add(implementationConfigName, libs.findLibrary("koin-androidx-compose").get())
            add(implementationConfigName, libs.findLibrary("kotlinx-coroutines-core").get())
            add(implementationConfigName, libs.findLibrary("kotlinx-coroutines-android").get())
            add(implementationConfigName, libs.findLibrary("lifecycle-viewmodel-ktx").get())
            add(implementationConfigName, libs.findLibrary("lifecycle-livedata-ktx").get())
            add(implementationConfigName, libs.findLibrary("navigation-ui-ktx").get())
            add(implementationConfigName, libs.findLibrary("androidx-navigation-compose").get())
            add(implementationConfigName, libs.findLibrary("firebase-bom").get())
            add(implementationConfigName, libs.findLibrary("androidx-compose-material3").get())
        }
    }
}
