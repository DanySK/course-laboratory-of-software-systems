// declare a "configuration" named "someConfiguration"
val someConfiguration by configurations.creating

dependencies {
    // add a project dependency to the "someConfiguration" configuration
    someConfiguration(project(":lib"))
}

// Create a configuration for the compilation,
// a dependency with a jar file,
// a compilation task
// and compile a simple Kotlin (or Java) file

tasks.register("hello") {
    doLast{ println("Hello") }
}

dependencies {

}

