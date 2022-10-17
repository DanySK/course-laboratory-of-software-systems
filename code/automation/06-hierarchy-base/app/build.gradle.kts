import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm

dependencies {
    compileClasspath(project(":library")) {
        targetConfiguration = "runtimeClasspath"
    }
}

tasks.compileJava {
    dependsOn(project(":library").tasks.compileJava)
}

tasks.register<Exec>("runJava") {
    val classpathFiles = configurations.runtimeClasspath.get().resolve()
    val mainClass = "PrintException" // Horribly hardcoded, we must do something
    val javaExecutable = Jvm.current().javaExecutable.absolutePath
    commandLine(
        javaExecutable,
        "-cp",
        classpathFiles.joinToString(separator = File.pathSeparator),
        mainClass
    )
    dependsOn(tasks.compileJava)
    mustRunAfter(tasks.named("clean"))
}
