package com.zachnr.bookplayfree.buildlogic.ext

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureUnitTest() {
    // TODO: To be implemented
}

internal fun Project.configureUITest() {
    val androidTestImp = "androidTestImplementation"
    val debugImpl = "debugImplementation"
    dependencies {
        add(androidTestImp, libs.findLibrary("androidx-compose-ui-test").get())
        add(debugImpl, libs.findLibrary("androidx-compose-ui-testManifest").get())
    }
}