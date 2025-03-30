plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.bpf.feature)
}

android {
    namespace = "com.zachnr.bookplayfree.splashscreen"
}

dependencies {
    implementation(libs.lottie.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}