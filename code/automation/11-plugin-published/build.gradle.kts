plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.7.10"
    id ("org.danilopianini.git-sensitive-semantic-versioning") version "0.3.0"
    id("com.gradle.plugin-publish") version "1.0.0"
}

group = "it.unibo.lss"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi()) // Implementation: available at compile and runtime, non transitive
    testImplementation(gradleTestKit()) // Test implementation: available for testing compile and runtime
    testImplementation("io.kotest:kotest-runner-junit5:5.4.2") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core:5.4.2") // for kotest core assertions
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.4.1") // for kotest core jvm assertions
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

// This task creates a file with a classpath descriptor, to be used in tests
val createClasspathManifest by tasks.registering {
    val outputDir = file("$buildDir/$name")
    inputs.files(sourceSets.main.get().runtimeClasspath)
    outputs.dir(outputDir)
    doLast {
        outputDir.mkdirs()
        file("$outputDir/plugin-classpath.txt").writeText(sourceSets.main.get().runtimeClasspath.joinToString("\n"))
    }
}

// Add the classpath file to the test runtime classpath
dependencies {
    // This way "createClasspathManifest" is always executed before the tests!
    // Gradle auto-resolves dependencies if there are dependencies on inputs/outputs
    testRuntimeOnly(files(createClasspathManifest))
}

pluginBundle { // These settings are set for the whole plugin bundle
    website = "https://danysk.github.io/Course-Laboratory-of-Software-Systems/"
    vcsUrl = "https://github.com/DanySK/Course-Laboratory-of-Software-Systems"
    tags = listOf("example", "greetings", "lss", "unibo")
}
gradlePlugin {
    plugins {
        create("GradleLatex") { // One entry per plugin
            id = "${project.group}.${project.name}"
            displayName = "LSS Greeting plugin"
            description = "Example plugin for the LSS course"
            implementationClass = "it.unibo.lss.firstplugin.GreetingPlugin"
        }
    }
}
