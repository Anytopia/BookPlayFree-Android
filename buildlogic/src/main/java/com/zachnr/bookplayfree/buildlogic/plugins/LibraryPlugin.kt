package com.zachnr.bookplayfree.buildlogic.plugins

import com.android.build.gradle.LibraryExtension
import com.zachnr.bookplayfree.buildlogic.ext.configureKotlinAndroid
import com.zachnr.bookplayfree.buildlogic.ext.configureUnitTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")
            apply(plugin = "io.gitlab.arturbosch.detekt")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureUnitTest()
            }
        }
    }
}