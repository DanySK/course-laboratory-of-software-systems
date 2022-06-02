plugins {
    java
    scala
    groovy
    kotlin("jvm") version "1.6.21"
}
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.codehaus.groovy:groovy:3.0.11")
    implementation(kotlin("stdlib"))
    implementation("org.scala-lang:scala-library:2.13.8")
}
