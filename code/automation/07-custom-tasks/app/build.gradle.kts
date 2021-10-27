import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.internal.jvm.Jvm
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

/*
 * Imperative logic
 */

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

    @org.gradle.api.tasks.Internal
    var classPath: Set<File> = FinderInFolder(project, "lib")
            .withExtension("jar")
            .map { project.file(it) }
            .toSet()
        protected set

    private val classPathDescriptor get() = classPath.joinToString(separator = separator)

    fun fromConfiguration(configuration: Configuration) {
        classPath = configuration.resolve().filterNotNull().toSet()
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

open class RunJava @javax.inject.Inject constructor() : JavaTask() {

    @org.gradle.api.tasks.Internal
    var mainClass: String = "Main"
        set(value) {
            field = value
            update()
        }

    init {
        fromConfiguration(project.configurations.runtimeClasspath.get())
        dependsOn(project.tasks.named("compileJava")) // Sub-optimal, but we don't have access to the type!
    }

   final override fun update() { javaCommandLine(mainClass) }
}

/*
 * Declarative configuration
 */

dependencies {
    compileClasspath(project(":library")) {
        targetConfiguration = "runtimeClasspath"
    }
}

tasks.compileJava {// The CompileJava type is not visible, nor configurable!
    (this as Task).dependsOn(project(":library").tasks.compileJava)
    // The only way to configure is to go via reflection! ARGH
    this::class.memberFunctions.find { it.name == "fromConfiguration" }
        ?.call(this, configurations.compileClasspath.get())
            ?: throw IllegalStateException()
}

tasks.register<RunJava>("runJava") {
    mainClass = "PrintException" // No longer hardcoded as before!
}
