plugins {
    `java-gradle-plugin` // Loads the plugin in the test classpath
    kotlin("jvm") version "1.7.20"
}

// Configuration of software sources
repositories {
    mavenCentral()
}

dependencies {
    /*
     * "implementation" and "testImplementation" are configurations introduced by the Kotlin plugin.
     */
    implementation(gradleApi()) // Implementation: available at compile and runtime, non transitive
    testImplementation(gradleTestKit()) // Test implementation: available for testing compile and runtime
    testImplementation("io.kotest:kotest-runner-junit5:5.5.3") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core:5.5.3") // for kotest core assertions
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.3") // for kotest core jvm assertions
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

//gradlePlugin {
//    plugins {
//        create("") { // One entry per plugin
//            id = "${project.group}.${project.name}"
//            displayName = "LSS Greeting plugin"
//            description = "Example plugin for the LSS course"
//            implementationClass = "it.unibo.firstplugin.GreetingPlugin"
//        }
//    }
//}
