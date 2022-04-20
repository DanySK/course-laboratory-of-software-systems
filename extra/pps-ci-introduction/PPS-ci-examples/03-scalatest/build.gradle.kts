plugins {
    java
    scala
    id("org.scoverage") version "7.0.0"
    id("com.github.maiflai.scalatest") version "0.32"
}

repositories {
    mavenCentral()
}

dependencies {
    val scalaVersion = "2.13.6"
    val (scalaMinor, _) = requireNotNull(Regex("^(\\d+\\.\\d+)(\\.\\d+)?$").matchEntire(scalaVersion)).destructured
    implementation("org.scala-lang:scala-library:$scalaVersion")
    testImplementation("org.scalatest:scalatest_$scalaMinor:3.3.0-SNAP3")
    testImplementation("org.scalamock:scalamock_$scalaMinor:5.2.0")
    testRuntimeOnly("com.vladsch.flexmark:flexmark-profile-pegdown:0.36.8")
    val scoverageVersion = "1.4.11"
    scoverage("org.scoverage:scalac-scoverage-plugin_$scalaVersion:$scoverageVersion")
    scoverage("org.scoverage:scalac-scoverage-runtime_$scalaMinor:$scoverageVersion")
}

// Force a scoverage report if check is requested
tasks.check {
    dependsOn(tasks.reportScoverage)
}
