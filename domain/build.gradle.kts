plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.zachnr.bookplayfree.domain"
}

dependencies {
    api(libs.ktor.serialization.kotlinx.json)
}
