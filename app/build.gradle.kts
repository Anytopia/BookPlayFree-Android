import com.zachnr.bookplayfree.buildlogic.utils.Modules
import io.gitlab.arturbosch.detekt.Detekt

val setupGitHooksTaskName = "setupGitHooks"

plugins {
    alias(libs.plugins.bpf.application)
    alias(libs.plugins.bpf.compose.app)
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

tasks.register<Exec>(setupGitHooksTaskName) {
    onlyIf { file("${rootDir}/.git").exists() }
    workingDir = rootDir
    commandLine("git", "config", "core.hooksPath", ".githooks")
}

afterEvaluate {
    tasks.findByName("preBuild")?.dependsOn(setupGitHooksTaskName)
}