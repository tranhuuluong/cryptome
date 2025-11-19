plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk {
        version = release(AppConfig.compileSdk)
    }

    defaultConfig {
        minSdk = AppConfig.minSdk
    }

    compileOptions {
        sourceCompatibility = AppConfig.sourceCompatibility
        targetCompatibility = AppConfig.targetCompatibility
    }

    kotlin {
        compilerOptions {
            jvmTarget = AppConfig.jvmTarget
            freeCompilerArgs.addAll(AppConfig.commonOptIns)
        }
    }
}

dependencies {
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
}