import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val VersionCatalog.pluginKotlinCompose
    get() = findPluginOrThrow("kotlin-compose")

internal val VersionCatalog.composeBom
    get() = findLibraryOrThrow("androidx-compose-bom")

internal val VersionCatalog.composeBundle
    get() = findBundleOrThrow("compose")

internal val VersionCatalog.composeUiTestJUnit4
    get() = findLibraryOrThrow("androidx-compose-ui-test-junit4")

internal val VersionCatalog.composeUiTestManifest
    get() = findLibraryOrThrow("androidx-compose-ui-test-manifest")

internal val VersionCatalog.composeUiTooling
    get() = findLibraryOrThrow("androidx-compose-ui-tooling")

internal val VersionCatalog.junit
    get() = findLibraryOrThrow("junit")

private fun VersionCatalog.findPluginOrThrow(name: String) =
    findPlugin(name)
        .orElseThrow { NoSuchElementException("Plugin $name not found in version catalog") }

private fun VersionCatalog.findLibraryOrThrow(name: String) =
    findLibrary(name)
        .orElseThrow { NoSuchElementException("Library $name not found in version catalog") }

private fun VersionCatalog.findBundleOrThrow(name: String) =
    findBundle(name)
        .orElseThrow { NoSuchElementException("Bundle $name not found in version catalog") }