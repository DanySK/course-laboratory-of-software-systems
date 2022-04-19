plugins {
    java
    scala
    jacoco
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:+")
    testImplementation("org.junit.jupiter:junit-jupiter-api:+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:+")
}

// Enables JUnit Platform (needed for JUnit 5)
tasks.named<Test>("test") {
    useJUnitPlatform()
}
