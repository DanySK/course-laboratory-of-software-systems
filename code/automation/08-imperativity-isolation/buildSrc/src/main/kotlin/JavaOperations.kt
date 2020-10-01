import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.*
import org.gradle.internal.jvm.Jvm
import org.gradle.kotlin.dsl.withType
import java.io.File
import javax.inject.Inject

// Inject annotation is required!
open class Clean @Inject constructor() : DefaultTask() { // Must be open: Gradle generates subclasses at runtime
    @TaskAction
    fun clean() {
        if (!project.buildDir.deleteRecursively()) {
            throw IllegalStateException("Cannot delete ${project.buildDir}")
        }
    }
}
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

    @Input
    var classPath: Set<File> = FinderInFolder(project, "lib")
            .withExtension("jar")
            .map { project.file(it) }
            .toSet()
        protected set
    private val classPathDescriptor get() = classPath.joinToString(separator = separator)

    fun fromConfiguration(configuration: Configuration) {
        classPath = configuration.resolve()
        update()
    }

    fun javaCommandLine(vararg arguments: String) = commandLine(
            executable,
            *(if (classPath.isEmpty()) emptyArray() else arrayOf("-cp", classPathDescriptor)),
            *arguments
    )

    abstract fun update(): Unit

    companion object {
        val separator = if (Os.isFamily(Os.FAMILY_WINDOWS)) ";" else ":"
    }
}

open class CompileJava @javax.inject.Inject constructor() : JavaTask(Jvm.current().javacExecutable) {
    @OutputDirectory
    var outputFolder: String = "${project.buildDir}/bin/"
        set(value) {
            field = value
            update()
        }
    // The shorter version does not work due to a Gradle/Kotlin bug
    @InputFiles
    var sources: Set<String> = FinderInFolder(project, "src").withExtension("java").toSet()
        set(value) {
            field = value
            update()
        }
    init { update() }

    final override fun update() {
        javaCommandLine("-d", outputFolder, *sources.toTypedArray())
    }
}

open class RunJava @javax.inject.Inject constructor() : JavaTask() {
    @Input
    var mainClass: String = "Main"
        set(value) {
            field = value
            update()
        }

    init {
        dependsOn(project.tasks.withType<CompileJava>())
    }

    final override fun update() { javaCommandLine(mainClass) }
}
