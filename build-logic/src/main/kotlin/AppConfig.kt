import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object AppConfig {
    const val compileSdk = 36
    const val minSdk = 24
    const val versionCode = 1
    const val versionName = "1.0"
    val sourceCompatibility = JavaVersion.VERSION_11
    val targetCompatibility = JavaVersion.VERSION_11
    val jvmTarget = JvmTarget.JVM_11
}