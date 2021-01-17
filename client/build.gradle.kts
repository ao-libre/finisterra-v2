import org.gradle.internal.os.OperatingSystem

plugins {
    id("finisterra.artemis-conventions")
    id("finisterra.native-conventions")
}

dependencies {
    implementation(project(":client:components"))
    api(project(":shared"))
    api(arcModule("arc-core"))
    implementation(arcModule("backend-sdl"))
    implementation(arcModule("natives-desktop"))
    implementation("com.artemis:artemis-odb:0.0.2-SNAPSHOT")
}

val os: OperatingSystem = OperatingSystem.current()

application {
    mainClass.set("Launcher")

    @Suppress("DEPRECATION") // it will be deprecated in gradle 8 but shadow jar still not supports `mainClass`
    mainClassName = "Launcher"

    if(os.isMacOsX) {
        applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.internal.JavaJarExec>("runShadow") {
    if(os.isMacOsX) {
        jvmArgs = listOf("-XstartOnFirstThread")
    }
}