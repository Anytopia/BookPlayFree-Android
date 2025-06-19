plugins {
    alias(libs.plugins.bpf.library)
}

android {
    namespace = "com.zachnr.bookplayfree.utils"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}
