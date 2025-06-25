// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.detekt)
}

// Task to generate combined coverage report for all modules
tasks.register<JacocoReport>("jacocoRootReport") {
    dependsOn(subprojects.map { it.tasks.matching { task -> task.name == "jacocoTestReport" } })

    val reportTasks = subprojects.mapNotNull { subproject ->
        subproject.tasks.findByName("jacocoTestReport") as? JacocoReport
    }

    sourceDirectories.setFrom(reportTasks.map { it.sourceDirectories })
    classDirectories.setFrom(reportTasks.map { it.classDirectories })
    executionData.setFrom(reportTasks.map { it.executionData })

    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)

        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/jacocoRootReport/html"))
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/jacocoRootReport/jacocoRootReport.xml"))
    }
}

// Task to run all tests across modules
tasks.register("testAll") {
    dependsOn(subprojects.map { it.tasks.matching { task -> task.name == "test" || task.name == "testDebugUnitTest" } })
}