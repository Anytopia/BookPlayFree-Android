package com.zachnr.bookplayfree.buildlogic.plugins

import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import com.zachnr.bookplayfree.buildlogic.ext.configureUnitTest
import com.zachnr.bookplayfree.buildlogic.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "androidx.room")
            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
            }
            extensions.configure<RoomExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                schemaDirectory("$projectDir/schemas")
            }
            configureRoomDependencies()
            configureUnitTest()
        }
    }

    private fun Project.configureRoomDependencies() {
        val implementationConfigName = "implementation"
        val kspConfigName = "ksp"

        dependencies {
            add(implementationConfigName, libs.findLibrary("room-runtime").get())
            add(implementationConfigName, libs.findLibrary("room-ktx").get())
            add(kspConfigName, libs.findLibrary("room-compiler").get())
        }
    }
}
