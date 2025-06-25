plugins {
    `kotlin-dsl`
}

group = "com.zachnr.bookplayfree.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("bpfApplicationPlugin") {
            id = libs.plugins.bpf.application.get().pluginId
            implementationClass = "com.zachnr.bookplayfree.buildlogic.plugins.ApplicationPlugin"
        }
        create("bpfComposeApplicationPlugin") {
            id = libs.plugins.bpf.compose.app.get().pluginId
            implementationClass =
                "com.zachnr.bookplayfree.buildlogic.plugins.ComposeApplicationPlugin"
        }
        create("bpfComposeLibraryPlugin") {
            id = libs.plugins.bpf.compose.lib.get().pluginId
            implementationClass = "com.zachnr.bookplayfree.buildlogic.plugins.ComposeLibraryPlugin"
        }
        create("bpfLibraryPlugin") {
            id = libs.plugins.bpf.library.get().pluginId
            implementationClass = "com.zachnr.bookplayfree.buildlogic.plugins.LibraryPlugin"
        }
        create("bpfFeaturePlugin") {
            id = libs.plugins.bpf.feature.get().pluginId
            implementationClass = "com.zachnr.bookplayfree.buildlogic.plugins.FeaturePlugin"
        }
        create("bpfRoomPlugin") {
            id = libs.plugins.bpf.room.get().pluginId
            implementationClass = "com.zachnr.bookplayfree.buildlogic.plugins.RoomPlugin"
        }
        create("bpfJacocoPlugin") {
            id = libs.plugins.bpf.jacoco.get().pluginId
            implementationClass =
                "com.zachnr.bookplayfree.buildlogic.plugins.JacocoConventionPlugin"
        }
    }
}
