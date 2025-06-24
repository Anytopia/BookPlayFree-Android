import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
}

android {
    namespace = "com.zachnr.bookplayfree.utils"
}

dependencies {
    implementation(project(Modules.Core.TEST))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}
