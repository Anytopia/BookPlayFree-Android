import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.bpf.compose.lib)
}

android {
    namespace = "com.zachnr.bookplayfree.uicomponent"
}

dependencies {
    implementation(project(Modules.Core.NAVIGATION))
    implementation(project(Modules.Core.DESIGN_SYSTEM))
    implementation(project(Modules.Core.UTILS))
    implementation(project(Modules.Core.TEST))

    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.lottie.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.constraintlayout.core)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}