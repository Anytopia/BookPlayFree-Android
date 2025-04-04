plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.zachnr.bookplayfree.domain"
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    api(libs.ktor.serialization.kotlinx.json)
}
