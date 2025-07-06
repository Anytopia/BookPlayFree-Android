import com.android.build.api.dsl.ApplicationDefaultConfig
import com.zachnr.bookplayfree.buildlogic.utils.Modules
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.bpf.application)
    alias(libs.plugins.bpf.compose.app)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.zachnr.bookplayfree"

    defaultConfig {
        multiDexEnabled = true
    }
}

dependencies {
    implementation(project(Modules.SHARED))
    implementation(project(Modules.DATA))
    implementation(project(Modules.DOMAIN))

    implementation(project(Modules.Core.AI_LOCAL))
    implementation(project(Modules.Core.DESIGN_SYSTEM))
    implementation(project(Modules.Core.NAVIGATION))
    implementation(project(Modules.Core.NETWORK))
    implementation(project(Modules.Core.UTILS))
    implementation(project(Modules.Core.TEST))
    implementation(project(Modules.Core.FIREBASE))
    implementation(project(Modules.Core.DATA_STORE))

    implementation(project(Modules.Features.DASHBOARD))
    implementation(project(Modules.Features.SPLASH_SCREEN))

    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.startup.runtime)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.config)

    // Leak canary
    debugImplementation(libs.leakcanary.android)
}

/**
 * This function is to configure filtering abi
 * TODO: To be researched
 */
private fun ApplicationDefaultConfig.configureNdkFilter() {
    val isBuildForPlayStore =
        project.hasProperty("playStore") && project.property("playStore") == "true"
    ndk {
        if (isBuildForPlayStore) {
            abiFilters.addAll(listOf("arm64-v8a"))
        }
    }
}

// =========== DETEKT SET-UP  ===========
tasks.withType<Detekt>().configureEach {
    val inputParam = project.findProperty("detekt.input") as String?
    setSource(
        if (!inputParam.isNullOrBlank()) {
            inputParam.split(",").map { file(it.trim()) }
        } else {
            listOf(
                "src/main/java",
                "src/main/kotlin",
                "src/test/java",
                "src/test/kotlin",
                "src/androidTest/java",
                "src/androidTest/kotlin"
            ).map { file(it) }
        }
    )
}
// =========== END DETEKT SET-UP ===========

// =========== GIT HOOKS SET-UP ===========
val setupGitHooksTaskName = "setupGitHooks"
tasks.register<Exec>(setupGitHooksTaskName) {
    val gitDir = layout.projectDirectory.dir(".git")
    val markerFile = layout.projectDirectory.file("gitHooksSetupDone.marker")

    onlyIf {
        gitDir.asFile.exists() && !markerFile.asFile.exists()
    }

    workingDir = rootDir

    val isWindows = System.getProperty("os.name").startsWith("Windows")
    if (isWindows) {
        commandLine(
            "cmd",
            "/c",
            "git config --local --unset core.hooksPath || exit 0 && git config --local core.hooksPath .githooks"
        )
    } else {
        commandLine(
            "sh",
            "-c",
            "git config --local --unset core.hooksPath || true && git config --local core.hooksPath .githooks"
        )
    }

    doLast {
        val marker = markerFile.asFile
        marker.parentFile.mkdirs()
        marker.writeText("Git hooks setup completed.")
    }
}

afterEvaluate {
    tasks.findByName("preBuild")?.dependsOn(setupGitHooksTaskName)
}
// =========== END GIT HOOKS SET-UP ===========
