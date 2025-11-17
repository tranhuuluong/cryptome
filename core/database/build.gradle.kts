plugins {
    alias(libs.plugins.cryptome.module.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.luongtran.cryptome.core.database"
}

dependencies {
    ksp(libs.androidx.room.compiler)
    implementation(libs.bundles.room)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}