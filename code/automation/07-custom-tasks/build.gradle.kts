import org.gradle.internal.jvm.Jvm

/*
 * Imperative logic
 */

open class Clean @Inject constructor() : DefaultTask() { // Must be open: Gradle generates subclasses at runtime
    @TaskAction
    fun clean() {
        check(project.buildDir.deleteRecursively()) {
            "Cannot delete ${project.buildDir}"
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

/*
 * This is horrible and unmaintanable cut/paste!
 */
abstract class JavaTask(javaExecutable: File = Jvm.current().javaExecutable) : Exec() {

    init {
        executable = javaExecutable.absolutePath
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

/*
 * Declarative configuration
 */
allprojects {
    val clean by tasks.registering(Clean::class)
    tasks.withType<JavaCompile> { mustRunAfter(clean) }
    tasks.withType<JavaExec> { mustRunAfter(clean) }
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
