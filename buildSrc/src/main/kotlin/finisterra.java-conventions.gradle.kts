plugins {
    `java-library`
    checkstyle
}

repositories {
    jcenter()
}

checkstyle {
    config = resources.text.fromString(finisterra.CheckstyleUtil.getCheckstyleConfig("/checkstyle.xml"))
    maxWarnings = 0
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:deprecation")
}