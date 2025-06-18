package com.zachnr.bookplayfree.buildlogic.ext

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure detekt dependency
 */
internal fun Project.configureDetekt() {
    extensions.configure<DetektExtension> {
        autoCorrect = true
        buildUponDefaultConfig = true
    }

    val implementationConfigName = "detektPlugins"
    dependencies {
        add(implementationConfigName, libs.findLibrary("detekt-formatting").get())
    }
}
