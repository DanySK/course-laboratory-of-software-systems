#!/usr/bin/env kotlin

// DSL Container (optional)
object HTMLDSL {
    // Domain model

    interface Element {
        fun render(indent: String = ""): String
    }

    interface RepeatableElement : Element

    interface Tag : Element {
        val name: String
        val children: List<Element>
        val attributes: Map<String, String>
    }

    interface RepeatableTag : Tag, RepeatableElement

    interface TextElement : RepeatableElement {
        val text: String
        override fun render(indent: String) = "$indent$text\n"
    }

    interface Attribute {
        val key: String
        val value: String
        operator fun component1() = key
        operator fun component2() = value
    }

    // Domain implementation

    const val INDENT = "\t"

    data class Text(override val text: String) : TextElement

    // Attribute construction with infix method
    infix fun String.equal(value: String) = object : Attribute {
        override val key = this@equal
        override val value = value
    }

    abstract class AbstractTag(override val name: String, vararg attributes: Attribute) : Tag {

        final override var children: List<Element> = emptyList() // Override val with var
            private set(value) { field = value } // Write access only from this class

        final override val attributes: Map<String, String> = attributes.associate { it.key to it.value }

        fun registerElement(element: Element) {
            require(element is RepeatableElement || children.none { it::class == element::class }) {
                "cannot repeat tag ${element::class.simpleName} multiple times:\n$element"
            }
            children = children + element
        }

        final override fun render(indent: String) = // Rendering by multiline string!
            """
            |$indent<$name${renderAttributes()}>
            |${renderChildren(indent)}
            |$indent</$name>
            """
            .trimMargin() // Trims what's left of a |
            .replace("""\R+""".toRegex(), "\n") // In case there are no children, no empty lines

        private fun renderChildren(indent: String): String =
            children.map { it.render(indent + INDENT) }.joinToString(separator = "\n")

        private fun renderAttributes(): String = attributes.takeIf { it.isNotEmpty() }
            ?.map { (attribute, value) -> "$attribute=\"$value\"" } // Safe fluent calls
            ?.joinToString(separator = " ", prefix = " ")
            ?: "" // Elvis operator
    }

    // Domain instance

    class HTML(vararg attributes: Attribute = arrayOf()) : AbstractTag("html", *attributes) {
        fun head(configuration: Head.() -> Unit = { }) = registerElement(Head().apply(configuration))
    }

    abstract class TagWithText(name: String, vararg attributes: Attribute) : AbstractTag(name, *attributes) {
        // Scoping via member extensions!
        operator fun String.unaryMinus() = registerElement(Text(this)) // Syntax for writing plain text
    }

    class Head : AbstractTag("head") {
        fun title(configuration: Title.() -> Unit = { }) = registerElement(Title().apply(configuration))
    }

    class Title : TagWithText("title")

    // DSL wrapper

    fun html(vararg attributes: Attribute, init: HTML.() -> Unit): HTML =
        HTML(*attributes).apply(init)
}

fun main() { // this -> undefined
    with(HTMLDSL) { // this: HTMLDSL
        val result =
            html ("lang" equal "it") {  // this: HTML
                head {
                    title {
                        - "This is my title"
                        - "Ciao CIao ciao"
                    }
                }
            }.render()
        println(result)
        println(html { head { head { title { title { } } } } }) // ERROR!
    }
}

main()
