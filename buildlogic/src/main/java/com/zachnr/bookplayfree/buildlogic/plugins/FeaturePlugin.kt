package com.zachnr.bookplayfree.buildlogic.plugins

import com.android.build.gradle.LibraryExtension
import com.zachnr.bookplayfree.buildlogic.ext.configureDetekt
import com.zachnr.bookplayfree.buildlogic.ext.configureUITest
import com.zachnr.bookplayfree.buildlogic.ext.configureUnitTest
import com.zachnr.bookplayfree.buildlogic.ext.libs
import com.zachnr.bookplayfree.buildlogic.utils.Modules
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class FeaturePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureFeaturePlugins()
            configureBuildFeatures()
            configureFeatureDependencies()
            configureDetekt()
            configureUITest()
            configureUnitTest()
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
        val impl = "implementation"
        dependencies {
            // Modules
            add(impl, project(Modules.SHARED))
            add(impl, project(Modules.DATA))
            add(impl, project(Modules.DOMAIN))
            add(impl, project(Modules.Core.DESIGN_SYSTEM))
            add(impl, project(Modules.Core.NAVIGATION))
            add(impl, project(Modules.Core.NETWORK))
            add(impl, project(Modules.Core.UI_COMPONENT))
            add(impl, project(Modules.Core.UTILS))
            add(impl, project(Modules.Core.TEST))

            // External libraries
            add(impl, libs.findLibrary("androidx-core-ktx").get())
            add(impl, libs.findLibrary("androidx-appcompat").get())
            add(impl, libs.findLibrary("androidx-material3-android").get())
            add(impl, libs.findLibrary("koin-core").get())
            add(impl, libs.findLibrary("koin-android").get())
            add(impl, libs.findLibrary("koin-androidx-compose").get())
            add(impl, libs.findLibrary("koin-androidx-compose-navigation").get())
            add(impl, libs.findLibrary("kotlinx-coroutines-core").get())
            add(impl, libs.findLibrary("kotlinx-coroutines-android").get())
            add(impl, libs.findLibrary("lifecycle-viewmodel-ktx").get())
            add(impl, libs.findLibrary("lifecycle-livedata-ktx").get())
            add(impl, libs.findLibrary("navigation-ui-ktx").get())
            add(impl, libs.findLibrary("androidx-navigation-compose").get())
            add(impl, libs.findLibrary("firebase-bom").get())
            add(impl, libs.findLibrary("androidx-compose-material3").get())
        }
    }
}
