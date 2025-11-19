plugins {
    id("android-library-convension")
}

apply { plugin(libs.pluginKotlinCompose.get().pluginId) }

android {
    buildFeatures {
        compose = true
    }
    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            )
        }
    }
}

dependencies {
    implementation(platform(libs.composeBom))
    implementation(libs.composeBundle)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.composeUiTestJUnit4)
    debugImplementation(libs.composeUiTooling)
    debugImplementation(libs.composeUiTestManifest)
}