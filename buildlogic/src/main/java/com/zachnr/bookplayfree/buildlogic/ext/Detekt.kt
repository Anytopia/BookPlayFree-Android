package com.zachnr.bookplayfree.buildlogic.ext

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure detekt dependency
 */
internal fun Project.configureDetekt() {
    val implementationConfigName = "detektPlugins"
    dependencies {
        add(implementationConfigName, libs.findLibrary("detekt-formatting").get())
    }
}
