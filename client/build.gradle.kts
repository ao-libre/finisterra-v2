import org.gradle.internal.os.OperatingSystem

plugins {
    id("finisterra.artemis-conventions")
}

dependencies {
    implementation(project(":client:components"))
    implementation(project(":shared"))
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.9.14")
    implementation("com.badlogicgames.gdx:gdx-platform:1.9.14:natives-desktop")
    implementation ("com.badlogicgames.gdx:gdx-freetype:1.9.14")
    implementation("com.artemis:artemis-odb:0.0.2-SNAPSHOT")
    implementation("org.reflections:reflections:0.9.12")
}

val os: OperatingSystem = OperatingSystem.current()

sourceSets.main {
    java.srcDir("src/main/java")
    resources.srcDir("../assets")
}

application {
    mainClass.set("Launcher")

    @Suppress("DEPRECATION") // it will be deprecated in gradle 8 but shadow jar still not supports `mainClass`
    mainClassName = "Launcher"

    if(os.isMacOsX) {
        applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
    }
}