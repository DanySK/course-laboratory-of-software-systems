import de.fayard.refreshVersions.bootstrapRefreshVersions
buildscript {
    repositories { gradlePluginPortal() }
    dependencies {
        classpath("de.fayard.refreshVersions:refreshVersions:0.23.0")
    }
}
bootstrapRefreshVersions()

rootProject.name = "greetings-plugin-complete"
