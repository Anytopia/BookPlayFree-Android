import com.zachnr.bookplayfree.buildlogic.utils.Modules
import java.util.Properties

plugins {
    alias(libs.plugins.bpf.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.zachnr.bookplayfree.network"

    val properties = Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }
    val apiKey = properties.getProperty("DEEPSEEK_API_KEY") ?: ""
    buildTypes {
        debug {
            buildConfigField("String", "DEEPSEEK_API_KEY", "\"$apiKey\"")
        }

        release {
            buildConfigField("String", "DEEPSEEK_API_KEY", "\"$apiKey\"")
        }
    }
}

dependencies {
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.Core.UTILS))
    implementation(project(Modules.Core.TEST))

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    api(libs.ktor.client.core)
    api(libs.ktor.client.okhttp)
    api(libs.ktor.client.logging)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.client.cio)
    api(libs.ktor.serialization.kotlinx.json)
}