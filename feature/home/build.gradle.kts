plugins {
    alias(libs.plugins.cryptome.module.compose)
}

android {
    namespace = "com.luongtran.cryptome.feature.home"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}