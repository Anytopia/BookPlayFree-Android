package com.zachnr.bookplayfree.buildlogic.ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.5"
        }

        dependencies {
            val bom = libs.findLibrary("androidx-compose-bom").get()
            val impl = "implementation"
            val debugImpl = "debugImplementation"
            val androidTestImpl = "debugImplementation"
            add(impl, platform(bom))
            add(androidTestImpl, platform(bom))
            add(impl, libs.findLibrary("androidx.navigation.compose").get())
            add(impl, libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add(debugImpl, libs.findLibrary("androidx-compose-ui-tooling").get())
        }
    }
}
