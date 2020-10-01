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
fun DependencyHandlerScope.forEachLibrary(todo: DependencyHandlerScope.(String) -> Unit) {
    findLibraries().forEach {
        todo(it)
    }
}
val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":"

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
    if (sources != null)  {
        // Use the current JVM's javac
        val javacExecutable = Jvm.current().javacExecutable.absolutePath
        commandLine(
            "$javacExecutable",
            "-cp", classpathFiles.joinToString(separator = separator),
            "-d", "$buildDir/bin/",
            *sources
        )
    }
}

tasks.register<Exec>("runJava") {
    val classpathFiles = runtimeClasspath.resolve()
    val mainClass = "PrintException" // Horribly hardcoded, we must do something
    val javaExecutable = Jvm.current().javaExecutable.absolutePath
    commandLine(
            "$javaExecutable",
            "-cp", classpathFiles.joinToString(separator = separator),
            mainClass
    )
    dependsOn(compileJava)
}
