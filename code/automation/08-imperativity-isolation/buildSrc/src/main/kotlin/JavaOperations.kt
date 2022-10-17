import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.jvm.Jvm
import org.gradle.kotlin.dsl.withType
import java.io.File
import javax.inject.Inject

open class Clean @Inject constructor() : DefaultTask() { // Must be open: Gradle generates subclasses at runtime
    @TaskAction
    fun clean() {
        check(project.buildDir.deleteRecursively()) {
            "Cannot delete ${project.buildDir}"
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

/*
 * This is horrible and unmaintanable cut/paste!
 */
abstract class JavaTask(javaExecutable: File = Jvm.current().javaExecutable) : Exec() {

    init {
        executable = javaExecutable.absolutePath
        mustRunAfter(project.tasks.withType<Clean>())
    }

    @Internal
    var classPath: Set<File> = FinderInFolder(project, "lib")
        .withExtension("jar")
        .map { project.file(it) }
        .toSet()
        protected set

    private val classPathDescriptor get() = classPath.joinToString(separator = File.pathSeparator)

    fun fromConfiguration(configuration: Configuration) {
        classPath = configuration.resolve()
        update()
    }

    fun javaCommandLine(vararg arguments: String) = commandLine(
        executable,
        *(if (classPath.isEmpty()) emptyArray() else arrayOf("-cp", classPathDescriptor)),
        *arguments
    )

    final override fun mustRunAfter(vararg paths: Any?): Task {
        return super.mustRunAfter(*paths)
    }

    abstract fun update()
}

open class CompileJava @Inject constructor() : JavaTask(Jvm.current().javacExecutable) {

    @OutputDirectory
    var outputFolder: String = "${project.buildDir}/bin/"
        set(value) {
            field = value
            update()
        }

    @Input
    // The shorter version does not work due to a Gradle/Kotlin bug
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
