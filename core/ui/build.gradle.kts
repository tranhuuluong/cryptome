plugins {
    alias(libs.plugins.cryptome.module.compose)
}

android {
    namespace = "com.luongtran.cryptome.core.ui"
}

dependencies {
    testImplementation(libs.robolectric)
}