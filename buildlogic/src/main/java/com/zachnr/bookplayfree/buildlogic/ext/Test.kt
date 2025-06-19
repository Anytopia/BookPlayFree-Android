package com.zachnr.bookplayfree.buildlogic.ext

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

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
    val testImp = "testImplementation"
    val androidTestImp = "androidTestImplementation"
    val debugImpl = "debugImplementation"
    dependencies {
        add(testImp, libs.findLibrary("robolectric").get())
        add(androidTestImp, libs.findLibrary("androidx-compose-ui-test").get())
        add(debugImpl, libs.findLibrary("androidx-compose-ui-testManifest").get())
    }
}