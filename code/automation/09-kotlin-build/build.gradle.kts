plugins {
    // No magic: calls a method running behind the scenes the same of id("org.jetbrains.kotlin-" + "jvm")
    kotlin("jvm") version "1.7.0" // version is necessary
}

// Configuration of software sources
repositories {
    jcenter()
    // mavenCentral() // points to Maven Central instead or additionally
}

dependencies {
    implementation(kotlin("stdlib-jdk8")) // Introduced by the Kotlin plugin
}
