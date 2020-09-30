allprojects {
    tasks.register<Clean>("clean")
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
