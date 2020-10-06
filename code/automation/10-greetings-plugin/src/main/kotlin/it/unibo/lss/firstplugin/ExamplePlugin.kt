package it.unibo.lss.firstplugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.provider.DefaultProperty
import org.gradle.api.internal.provider.ProviderInternal
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

class GreetingPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("greetings", ExampleExtension::class.java, target)
        target.tasks.register("greet", Greeting::class.java).get().run {
            greeting.set(extension.defaultGreeting)
        }
    }
}

open class ExampleExtension(val project: Project) {
    val defaultGreeting: Property<String> = project.objects.property(String::class.java)
        .apply { convention("Hello from") } // Set a conventional value

    // A DSL would go there
    fun greetWith(greeting: () -> String) = defaultGreeting.set(greeting())
}

open class Greeting : DefaultTask() {
    // Configurable by the user
    @Input
    val greeting: Property<String> = project.objects.property<String>(String::class.java)
    // Read-only property calculated from the greeting
    @Internal
    val message: Provider<String> = greeting.map { "$it Gradle" }
    @TaskAction
    fun printMessage() {
        logger.quiet(message.get())
    }
}
