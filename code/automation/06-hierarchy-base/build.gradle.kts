import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm

val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":"

allprojects {
    tasks.register("clean") { // A generic task is fine
        doLast {
            if (!buildDir.deleteRecursively()) {
                throw IllegalStateException("Cannot delete $buildDir")
            }
        }
    }
}

subprojects {
    /*
     * This must be there, as projectDir must refer to the *current* project
     */
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
    fun DependencyHandlerScope.forEachLibrary(todo: DependencyHandlerScope.(String) -> Unit) {
        findLibraries().forEach { todo(it) }
    }

    val compileClasspath by configurations.creating
    val runtimeClasspath by configurations.creating {
        extendsFrom(compileClasspath)
    }

    dependencies {
        forEachLibrary {
            compileClasspath(files(it))
        }
        runtimeClasspath(files("$buildDir/bin"))
    }

    tasks.register<Exec>("compileJava") {
        val classpathFiles = compileClasspath.resolve()
        // Build the command
        val sources = findSources()
        if (sources.isNotEmpty())  {
            // Use the current JVM's javac
            val javacExecutable = Jvm.current().javacExecutable.absolutePath
            commandLine(
                javacExecutable,
                "-cp", classpathFiles.joinToString(separator = separator),
                "-d", "$buildDir/bin/",
                *sources
            )
        } else {
            commandLine("echo", "No sources")
        }
    }
}
