package com.zachnr.bookplayfree.buildlogic.ext

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

// TODO: Make test convention plugin
internal fun Project.configureUnitTest() {
    val testImpl = "testImplementation"
    dependencies {
        add(testImpl, libs.findLibrary("kotlin-test").get())
        add(testImpl, libs.findLibrary("junit").get())
        add(testImpl, libs.findLibrary("mockk").get())
        add(testImpl, libs.findLibrary("kotlinx-coroutines-test").get())
    }
}

internal fun Project.configureUITest() {
    val testImpl = "testImplementation"
    val androidTestImpl = "androidTestImplementation"
    val debugImpl = "debugImplementation"
    dependencies {
        add(testImpl, libs.findLibrary("robolectric").get())
        add(androidTestImpl, libs.findLibrary("androidx-compose-ui-test").get())
        add(debugImpl, libs.findLibrary("androidx-compose-ui-testManifest").get())
    }
}