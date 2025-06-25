package com.zachnr.bookplayfree.buildlogic.plugins

import com.zachnr.bookplayfree.buildlogic.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension

class JacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(JacocoPlugin::class.java)
            }

            configure<JacocoPluginExtension> {
                toolVersion = libs.findVersion("jacoco").get().toString()
            }

            // Configure test tasks to generate coverage data
            tasks.withType<Test>().configureEach {
                configure<JacocoTaskExtension> {
                    isIncludeNoLocationClasses = true
                    excludes = listOf("jdk.internal.*")
                }
            }
        }
    }
}