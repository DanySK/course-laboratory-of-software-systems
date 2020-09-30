import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm

/*
 * Imperative logic
 */

// Inject annotation is required!
open class Clean @javax.inject.Inject constructor() : DefaultTask() { // Must be open: Gradle generates subclasses at runtime
    @org.gradle.api.tasks.TaskAction
    fun clean() {
        if (!project.buildDir.deleteRecursively()) {
            throw IllegalStateException("Cannot delete ${project.buildDir}")
        }
    }
}

// Modified to work with arbitrary projects
data class FinderInFolder(val project: Project, val directory: String) {
    fun withExtension(extension: String): Array<String> = project.projectDir
        .listFiles { it: File -> it.isDirectory && it.name == directory }
        ?.firstOrNull()
        ?.walk()
        ?.filter { it.extension == extension }
        ?.map { it.absolutePath }
        ?.toList()
        ?.toTypedArray()
        ?: emptyArray()
}
fun Project.findFilesIn(directory: String) = FinderInFolder(this, directory)
fun Project.findSources() = this.findFilesIn("src").withExtension("java")
fun Project.findLibraries() = this.findFilesIn("lib").withExtension("jar")

abstract class JavaTask(javaExecutable: File = Jvm.current().javaExecutable) : Exec() {

    init { executable = javaExecutable.absolutePath }

    var classPath: Set<File> = project.findLibraries().map { project.file(it) }.toSet()
        protected set
    val classPathDescriptor = classPath.joinToString(separator = separator) { it.absolutePath }

    fun fromConfiguration(configuration: Configuration) {
        classPath = configuration.resolve()
    }

    fun javaCommandLine(vararg arguments: String) = commandLine(
        executable,
        *(if (classPath.isEmpty()) emptyArray() else arrayOf("-cp", classPathDescriptor)),
        *arguments
    )

    companion object {
        val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":"
    }
}

open class CompileJava @javax.inject.Inject constructor() : JavaTask(Jvm.current().javacExecutable) {
//    var outputFolder: String = "${project.buildDir}/bin/"
//        set(value) {
//            field = value
//            update()
//        }
//    // The shorter version does not work due to a Gradle/Kotlin bug
//    var sources: Set<String> = FinderInFolder(project, "src").withExtension("java").toSet()
//        set(value) {
//            field = value
//            update()
//        }
//    init { update() }
//
//    fun update() = javaCommandLine("-d", outputFolder, *sources.toTypedArray()).also{println(args)}
}

//open class RunJava @javax.inject.Inject constructor() : JavaTask() {
//    var mainClass: String = "Main"
//        set(value) {
//            field = value
//            javaCommandLine(mainClass)
//        }
//}

/*
 * Declarative configuration
 */

allprojects {
    tasks.register<Clean>("clean")
}

subprojects {

    val compileClasspath by configurations.creating
    val runtimeClasspath by configurations.creating {
        extendsFrom(compileClasspath)
    }

    dependencies {
        findLibraries().forEach {
            compileClasspath(files(it))
        }
        runtimeClasspath(files("$buildDir/bin"))
    }

    tasks.register<CompileJava>("compileJava")
}
