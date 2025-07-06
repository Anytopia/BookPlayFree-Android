package com.zachnr.bookplayfree.buildlogic.plugins

import com.android.build.gradle.LibraryExtension
import com.zachnr.bookplayfree.buildlogic.ext.configureKotlinAndroid
import com.zachnr.bookplayfree.buildlogic.ext.configureUnitTest
import com.zachnr.bookplayfree.buildlogic.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "io.gitlab.arturbosch.detekt")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureUnitTest()
                configureLibDependency()
            }
        }
    }

    private fun Project.configureLibDependency() {
        val impl = "implementation"
        dependencies {
            // Koin
            add(impl, libs.findLibrary("koin-core").get())
            add(impl, libs.findLibrary("koin-android").get())
        }
    }
}