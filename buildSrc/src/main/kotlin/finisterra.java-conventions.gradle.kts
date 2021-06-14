plugins {
    `java-library`
    checkstyle
}

repositories {
    mavenCentral()
}

checkstyle {
    config = resources.text.fromString(finisterra.CheckstyleUtil.getCheckstyleConfig("/checkstyle.xml"))
    maxWarnings = 0
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:deprecation")
}