import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import java.io.File

class PluginTest : FreeSpec({
    val greetTask = ":greet" // Colon needed!
    fun testSetup(buildFile: () -> String) = projectSetup(buildFile).let { testFolder ->
        GradleRunner.create()
            .withProjectDir(testFolder.root)
            .withPluginClasspath()
            .withArguments(greetTask)
            .also {
                ClassLoader.getSystemResourceAsStream("testkit-gradle.properties")
                    ?.copyTo(
                        File("${testFolder.root.absolutePath}/gradle.pidearoperties")
                            .also { it.createNewFile() }
                            .outputStream()
                    )
                    ?: println(
                        "WARNING: Can't find testkit-gradle.properties, JaCoCo won't be able to measure the coverage"
                    )
            }
    }
    "running the plugin with" - {
        "default configuration should" - {
            val runner = testSetup {
                """
                    plugins {
                        id("it.unibo.lss.greetings-plugin")
                    }
                """.trimIndent()
            }.build()
            "run the greet task" {
                runner.task(greetTask)?.outcome shouldBe TaskOutcome.SUCCESS
            }
            "print an hello message" {
                runner.output shouldContain "Hello from Gradle"
            }
        }
        "a message in Italian" - {
            val runner = testSetup {
                """
                    plugins {
                        id("it.unibo.lss.greetings-plugin")
                    }
                    greetings {
                        greetWith { "Ciao da" }
                    }
                """.trimIndent()
            }.build()
            "run the greet task" {
                runner.task(greetTask)?.outcome shouldBe TaskOutcome.SUCCESS
            }
            "print an hello message" {
                runner.output shouldContain "Ciao da Gradle"
            }
        }
    }
})

fun projectSetup(content: String) = TemporaryFolder().apply {
    create()
    newFile("build.gradle.kts").writeText(content)
}

fun projectSetup(content: () -> String) = projectSetup(content())
