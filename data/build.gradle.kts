import com.zachnr.bookplayfree.buildlogic.utils.Modules

plugins {
    alias(libs.plugins.bpf.library)
}

android {
    namespace = "com.zachnr.bookplayfree.data"
}

dependencies {
    implementation(project(Modules.DOMAIN))
}
