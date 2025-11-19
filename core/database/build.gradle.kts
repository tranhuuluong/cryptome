plugins {
    alias(libs.plugins.cryptome.module.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.luongtran.cryptome.core.database"
}

dependencies {
    implementation(project(":core:domain"))
    ksp(libs.androidx.room.compiler)
    api(libs.bundles.room)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.core.ktx.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}