tasks.register("brokenTask") { // creates a new task
    println("this is executed at CONFIGURATION time!")
}

tasks.register("helloWorld") {
    doLast { println("Hello, World!") }
}

tasks.getByName("helloWorld") {
    doFirst {
        println("Configured later, executed first.")
    }  
}
