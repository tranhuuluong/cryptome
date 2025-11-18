plugins {
    alias(libs.plugins.cryptome.module.kotlin)
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.kotlin.coroutines.core)
}