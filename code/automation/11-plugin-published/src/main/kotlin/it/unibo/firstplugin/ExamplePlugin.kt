package it.unibo.firstplugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

class GreetingPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("greetings", GreetingExtension::class.java, target)
        target.tasks.register("greet", GreetingTask::class.java).get().run {
            greeting.set(extension.defaultGreeting)
        }
    }
}

open class GreetingExtension(val project: Project) {
    val defaultGreeting: Property<String> = project.objects.property(String::class.java)
        .apply { convention("Hello from") } // Set a conventional value

    // A DSL would go there
    fun greetWith(greeting: () -> String) = defaultGreeting.set(greeting())
}

open class GreetingTask : DefaultTask() {
    @Input
    val greeting: Property<String> = project.objects.property(String::class.java)
    @Internal // Read-only property calculated from `greeting`
    val message: Provider<String> = greeting.map { "$it Gradle" }
    @TaskAction
    fun printMessage() {
        logger.quiet(message.get())
    }
}
