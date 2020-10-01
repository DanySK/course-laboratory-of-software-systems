/*
 * Declarative configuration
 */
dependencies {
    compileClasspath(project(":library")) {
        targetConfiguration = "runtimeClasspath"
    }
}
tasks.compileJava {
    dependsOn(project(":library").tasks.compileJava)
    fromConfiguration(configurations.compileClasspath.get())
}

tasks.register<RunJava>("runJava") {
    fromConfiguration(configurations.runtimeClasspath.get())
    mainClass = "PrintException"
}
