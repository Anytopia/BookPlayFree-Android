import com.zachnr.bookplayfree.buildlogic.utils.Modules
import io.gitlab.arturbosch.detekt.Detekt
import java.time.Instant

plugins {
    alias(libs.plugins.bpf.application)
    alias(libs.plugins.bpf.compose.app)
}

android {
    namespace = "com.zachnr.bookplayfree"

    defaultConfig {
        multiDexEnabled = true
        ndk {
            // Include only arm64-v8a, exclude x86, x86_64, armeabi-v7a, etc.
            abiFilters.addAll(listOf("arm64-v8a"))
        }
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
    implementation(project(Modules.Features.DASHBOARD))
    implementation(project(Modules.Features.SPLASH_SCREEN))

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.startup.runtime)
}

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

// =========== GIT HOOKS SET-UP ===========
private val setupGitHooksTaskName = "setupGitHooks"
val markerFile = file("${buildDir}/gitHooksSetupDone.marker")
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
