import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.bpf.compose.lib)
}

android {
    namespace = "com.zachnr.bookplayfree.navigation"
}

dependencies {
    implementation(project(Modules.Core.TEST))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
}