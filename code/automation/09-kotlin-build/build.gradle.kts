plugins {
    // No magic: calls a method running behind the scenes the same of id("org.jetbrains.kotlin-" + "jvm")
    kotlin("jvm") version "1.7.20" // version is necessary
}

// Configuration of software sources
repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8")) // Introduced by the Kotlin plugin
}
