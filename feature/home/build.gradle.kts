plugins {
    alias(libs.plugins.cryptome.module.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.luongtran.cryptome.feature.home"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}