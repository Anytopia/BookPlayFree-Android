import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.zachnr.bookplayfree.domain"
}

dependencies {
    implementation(project(Modules.Core.UTILS))

    api(libs.ktor.serialization.kotlinx.json)
}
