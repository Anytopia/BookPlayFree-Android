import com.zachnr.bookplayfree.buildlogic.utils.Modules

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

tasks.register<Exec>("setupGitHooks") {
    onlyIf { file(".git").exists() }
    commandLine("git", "config", "core.hooksPath", ".githooks")
}

tasks.named("build") {
    dependsOn("setupGitHooks")
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