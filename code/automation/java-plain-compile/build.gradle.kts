import org.gradle.internal.jvm.Jvm

fun findSources(): Array<String> = projectDir
    .listFiles { it: File -> it.isDirectory && it.name == "src" }
    ?.firstOrNull()
    ?.walk()
    ?.filter { it.extension == "java" }
    ?.map { it.absolutePath }
    ?.toList()
    ?.toTypedArray()
    ?: emptyArray()

// Write a task of type Exec that launches javac
tasks.register<Exec>("compileJava") {
    // Search all java files in src
    val sources = findSources()
    if (sources.isNotEmpty())  { // If the folder exists and there are files
        // Use the current JVM's javac
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        commandLine(
            "$javacExecutable",
            "-d", "$buildDir/bin", // destination folder: the output directory of Gradle, inside "bin"
            *sources
        )
    }
    // the task's doLast is inherited from Exec
}
