import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":"

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
            "-cp", "$separator${classpathFiles.joinToString(separator = separator)}",
            mainClass
    )
    dependsOn(tasks.compileJava)
}
