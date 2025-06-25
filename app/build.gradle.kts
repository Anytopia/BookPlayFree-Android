import com.android.build.api.dsl.ApplicationDefaultConfig
import io.gitlab.arturbosch.detekt.Detekt
import java.time.Instant

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

/**
 * This function is to configure filtering abi
 * TODO: To be implemented
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
private val setupGitHooksTaskName = "setupGitHooks"
val markerFile = file("${rootDir}/gitHooksSetupDone.marker")
tasks.register<Exec>(setupGitHooksTaskName) {
    // Only run if .git folder exists and marker file does NOT exist
    onlyIf {
        file("${rootDir}/.git").exists() && !markerFile.exists()
    }

    workingDir = rootDir
    if (System.getProperty("os.name").startsWith("Windows")) {
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

    // After successful execution, create the marker file to mark completion
    doLast {
        markerFile.parentFile.mkdirs() // ensure directory exists
        markerFile.writeText("Git hooks setup completed at ${Instant.now()}")
    }
}

afterEvaluate {
    tasks.findByName("preBuild")?.dependsOn(setupGitHooksTaskName)
}
// =========== END GIT HOOKS SET-UP ===========
