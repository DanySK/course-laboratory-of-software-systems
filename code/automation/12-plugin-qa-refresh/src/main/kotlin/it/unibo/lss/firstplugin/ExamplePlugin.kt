package it.unibo.lss.firstplugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

/**
 * Greetings plugin.
 */
class GreetingPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("greetings", GreetingExtension::class.java, target)
        target.tasks.register("greet", GreetingTask::class.java).get().run {
            greeting.set(extension.defaultGreeting)
        }
    }
}

/**
 * Greetings extension, hosting the [defaultGreeting].
 */
open class GreetingExtension(project: Project) {

    /**
     * Default greeting.
     */
    val defaultGreeting: Property<String> = project.objects.property(String::class.java)
        .apply { convention("Hello from") } // Set a conventional value

    // A DSL would go there
    /**
     * Configures the greeting message.
     */
    fun greetWith(greeting: () -> String) = defaultGreeting.set(greeting())
}

/**
 * Greeting Task, with a [greeting] input and an internal [message] property.
 */
open class GreetingTask : DefaultTask() {

    /**
     * Greeting message.
     */
    @Input
    val greeting: Property<String> = project.objects.property(String::class.java)

    /**
     * Internal message provider.
     */
    @Internal // Read-only property calculated from `greeting`
    val message: Provider<String> = greeting.map { "$it Gradle" }

    /**
     * The task action.
     */
    @TaskAction
    fun printMessage() {
        logger.quiet(message.get())
    }
}
