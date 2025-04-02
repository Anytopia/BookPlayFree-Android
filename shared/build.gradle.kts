plugins {
    alias(libs.plugins.bpf.library)
}

android {
    namespace = "com.zachnr.bookplayfree.shared"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
