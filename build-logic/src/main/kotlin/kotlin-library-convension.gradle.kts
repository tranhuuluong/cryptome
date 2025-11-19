plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}
java {
    sourceCompatibility = AppConfig.sourceCompatibility
    targetCompatibility = AppConfig.targetCompatibility
}
kotlin {
    compilerOptions {
        jvmTarget = AppConfig.jvmTarget
        freeCompilerArgs.addAll(AppConfig.commonOptIns)
    }
}
