plugins {
    alias(libs.plugins.cryptome.module.compose)
}

android {
    namespace = "com.luongtran.cryptome.core.ui"
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    testImplementation(libs.robolectric)
}