plugins {
    java
    scala
    groovy
    kotlin("jvm") version "1.8.22"
}
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.codehaus.groovy:groovy:3.0.18")
    implementation(kotlin("stdlib"))
    implementation("org.scala-lang:scala3-library_3:3.3.0")
}
