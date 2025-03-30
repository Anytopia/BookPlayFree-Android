import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
}

android {
    namespace = "com.zachnr.bookplayfree.uicomponent"
}

dependencies {
    implementation(project(Modules.Core.NAVIGATION))
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}