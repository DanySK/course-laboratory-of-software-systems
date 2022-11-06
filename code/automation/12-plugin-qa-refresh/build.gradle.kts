import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    `java-gradle-plugin`
    jacoco
    kotlin("jvm") version "1.7.20"
    id("org.danilopianini.git-sensitive-semantic-versioning") version "0.3.0"
    id("com.gradle.plugin-publish") version "1.0.0"
    id("pl.droidsonroids.jacoco.testkit") version "1.0.9"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

group = "it.unibo.firstplugin"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi()) // Implementation: available at compile and runtime, non transitive
    testImplementation(gradleTestKit()) // Test implementation: available for testing compile and runtime
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core:5.5.4") // for kotest core assertions
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.3") // for kotest core jvm assertions
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.21.0")
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

pluginBundle { // These settings are set for the whole plugin bundle
    website = "https://danysk.github.io/Course-Laboratory-of-Software-Systems/"
    vcsUrl = "https://github.com/DanySK/Course-Laboratory-of-Software-Systems"
    tags = listOf("example", "greetings", "lss", "unibo")
}

gradlePlugin {
    plugins {
        create("") { // One entry per plugin
            id = "${project.group}.${project.name}"
            displayName = "LSS Greeting plugin"
            description = "Example plugin for the LSS course"
            implementationClass = "it.unibo.firstplugin.GreetingPlugin"
        }
    }
}

tasks.jacocoTestReport {
    reports {
        // xml.isEnabled = true
        html.required.set(true)
    }
}

// Disable JaCoCo on Windows, see: https://issueexplorer.com/issue/koral--/jacoco-gradle-testkit-plugin/9
tasks.jacocoTestCoverageVerification {
    enabled = !Os.isFamily(Os.FAMILY_WINDOWS)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        allWarningsAsErrors = true
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    config = files(File(projectDir, "config/detekt.yml"))
}
