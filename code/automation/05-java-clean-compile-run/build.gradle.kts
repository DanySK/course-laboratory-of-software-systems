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
fun DependencyHandlerScope.forEachLibrary(todo: DependencyHandlerScope.(String) -> Unit) {
    findLibraries().forEach {
        todo(it)
    }
}
fun Iterable<File>.asClasspathString() = joinToString(separator = File.pathSeparator)

val compileClasspath by configurations.creating
val runtimeClasspath by configurations.creating {
    extendsFrom(compileClasspath) // Built-in machinery to say that one configuration is like another "plus stuff"
}

dependencies {
    forEachLibrary {
        compileClasspath(files(it))
    }
    runtimeClasspath(files("$buildDir/bin"))
}

val compileJava = tasks.register<Exec>("compileJava") {
    val classpathFiles = compileClasspath.resolve()
    // Build the command
    val sources = findSources()
    if (sources.isNotEmpty())  {
        // Use the current JVM's javac
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        commandLine(
            javacExecutable,
            "-cp", classpathFiles.asClasspathString(),
            "-d", "$buildDir/bin/",
            *sources
        )
    }
}

val runJava by tasks.registering(Exec::class) {
    val classpathFiles = runtimeClasspath.resolve()
    val mainClass = "PrintException" // Horribly hardcoded, we must do something
    val javaExecutable = Jvm.current().javaExecutable.absolutePath
    commandLine(
        javaExecutable,
        "-cp", classpathFiles.asClasspathString(),
        mainClass
    )
    dependsOn(compileJava)
}

tasks.register("clean") { // A generic task is fine
    doLast {
        if (!buildDir.deleteRecursively()) {
            throw IllegalStateException("Cannot delete $buildDir")
        }
    }
    compileJava.get().mustRunAfter(this)
    runJava.get().mustRunAfter(this)
}
