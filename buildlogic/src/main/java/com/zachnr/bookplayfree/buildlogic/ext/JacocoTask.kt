package com.zachnr.bookplayfree.buildlogic.ext

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.tasks.JacocoReport

internal fun Project.configureJacocoTask(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        configure<BaseExtension> {
            buildTypes {
                getByName("debug") {
                    isTestCoverageEnabled = true
                }
            }
        }

        tasks.register<JacocoReport>("jacocoTestReport") {
            dependsOn("testDebugUnitTest")

            reports {
                xml.required.set(true)
                html.required.set(true)
                csv.required.set(false)
            }

            val fileFilter = listOf(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*",
                "android/**/*.*",
                "**/data/models/**",
                "**/di/**",
                "**/*_MembersInjector.class",
                "**/Dagger*Component*.class",
                "**/*Module_*Factory.class",
                "**/ComposableSingletons$*",
                "**/*\$serializer.class",
                "**/*_Factory.class",
                "**/BuildConfig.class",
                "**/*Composable*.*",
                "**/ui/theme/**"
            )

            val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
                exclude(fileFilter)
            }

            val mainSrc = "${project.projectDir}/src/main/java"
            val kotlinSrc = "${project.projectDir}/src/main/kotlin"

            sourceDirectories.setFrom(files(listOf(mainSrc, kotlinSrc)))
            classDirectories.setFrom(files(listOf(debugTree)))
            executionData.setFrom(fileTree(buildDir) {
                include("jacoco/testDebugUnitTest.exec")
            })
        }
    }
}