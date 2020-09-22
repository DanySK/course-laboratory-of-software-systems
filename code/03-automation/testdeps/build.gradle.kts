import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm

// Create a new configuration, our compileClasspath
val compileClasspath by configurations.creating

dependencies {
    // Search libraries in lib
    val libraries = projectDir
        .listFiles { it: File -> it.isDirectory && it.name == "lib"}
        ?.firstOrNull()
        ?.listFiles { it: File -> it.extension == "jar" }
        ?: emptyArray()
    println("Found libraries: ${libraries.joinToString()}")
    // Add them to the classpath
    compileClasspath(files(*libraries))
}

// Write a task of type Exec that launches javac
tasks.register<Exec>("compileJava") {
    // Resolve the classpath configuration
    // (in general, files could be remote and need fetching)
    val classpathFiles = compileClasspath.resolve()
    // Build the command
    val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":"
    val sources = projectDir
        .listFiles { it: File -> it.isDirectory && it.name == "src" }
        ?.firstOrNull()
        ?.walk()
        ?.filter { it.extension == "java" }
        ?.map { it.absolutePath }
        ?.toList()
        ?.toTypedArray()
    if (sources != null)  {
        // Use the current JVM's javac
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        commandLine(
            "$javacExecutable",
            "-cp", classpathFiles.joinToString(separator = separator),
            "-d", "bin",
            *sources
        )
    }
    // the task's doLast is inherited from Exec
}
