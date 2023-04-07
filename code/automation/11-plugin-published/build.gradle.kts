plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.8.20"
    id ("org.danilopianini.git-sensitive-semantic-versioning") version "1.1.5"
    // Generates the classpath manifest for us!
    id("com.gradle.plugin-publish") version "1.2.0"
}

group = "it.unibo.firstplugin"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi()) // Implementation: available at compile and runtime, non transitive
    testImplementation(gradleTestKit()) // Test implementation: available for testing compile and runtime
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core:5.5.5") // for kotest core assertions
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.5") // for kotest core jvm assertions
}

tasks.withType<Test> {
    useJUnitPlatform() // Use JUnit 5 engine
    testLogging.showStandardStreams = true
    testLogging {
        showCauses = true
        showStackTraces = true
        showStandardStreams = true
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

gradlePlugin {
    plugins {
        website.set("https://danysk.github.io/Course-Laboratory-of-Software-Systems")
        vcsUrl.set("https://github.com/DanySK/Course-Laboratory-of-Software-Systems.git")
        create("") { // One entry per plugin
            id = "${project.group}.${project.name}"
            displayName = "LSS Greeting plugin"
            description = "Example plugin for the LSS course"
            implementationClass = "it.unibo.firstplugin.GreetingPlugin"
            listOf("example", "greetings", "unibo")
        }
    }
}
