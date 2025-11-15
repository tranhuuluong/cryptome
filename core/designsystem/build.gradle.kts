plugins {
    alias(libs.plugins.cryptome.module.compose)
}

android {
    namespace = "com.luongtran.cryptome.core.designsystem"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}