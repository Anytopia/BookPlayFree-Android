import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.zachnr.bookplayfree.data"
}

dependencies {
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.SHARED))
    implementation(project(Modules.Core.NETWORK))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
