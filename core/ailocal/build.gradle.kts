import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
}

android {
    namespace = "com.zachnr.bookplayfree.ailocal"
}

dependencies {
    implementation(project(Modules.Core.UTILS))

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.translate)
    implementation(libs.language.id)
}
