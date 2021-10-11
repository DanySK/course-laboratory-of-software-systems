import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm

data class FinderInFolder(val directory: String) {
    fun withExtension(extension: String): Array<String> = projectDir
        .listFiles { it: File -> it.isDirectory && it.name == directory }
        ?.firstOrNull()
        ?.walk()
        ?.filter { it.extension == extension }
        ?.map { it.absolutePath }
        ?.toList()
        ?.toTypedArray()
        ?: emptyArray()
}
fun findFilesIn(directory: String) = FinderInFolder(directory)
fun findSources() = findFilesIn("src").withExtension("java")
fun findLibraries() = findFilesIn("lib").withExtension("jar")
fun forEachLibrary(todo: (String) -> Unit) {
    findLibraries().forEach {
        todo(it)
    }
}

// Create a new configuration, our compileClasspath
val compileClasspath by configurations.creating

dependencies {
    forEachLibrary {
        compileClasspath(files(it))
    }
}

// Write a task of type Exec that launches javac
tasks.register<Exec>("compileJava") {
    // Resolve the classpath configuration
    // (in general, files could be remote and need fetching)
    val classpathFiles = compileClasspath.resolve()
    // Build the command
    val sources = findSources()
    if (sources.isNotEmpty())  {
        // Use the current JVM's javac
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":"
        commandLine(
            "$javacExecutable",
            "-cp", classpathFiles.joinToString(separator = separator),
            "-d", "$buildDir/bin",
            *sources
        )
    }
    // the task's doLast is inherited from Exec
}

