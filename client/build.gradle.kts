import org.gradle.internal.os.OperatingSystem

plugins {
    id("finisterra.artemis-conventions")
}

val libgdxVersion: String by project
val artemisVersion: String by project
val reflectionsVersion: String by project

dependencies {
	implementation(projects.client.components)
	implementation(projects.shared)
	
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:${libgdxVersion}")
	implementation("com.badlogicgames.gdx:gdx-platform:${libgdxVersion}:natives-desktop")
	implementation("com.badlogicgames.gdx:gdx-freetype:${libgdxVersion}")
	implementation("com.badlogicgames.gdx:gdx-freetype-platform:${libgdxVersion}:natives-desktop")
	
	implementation("com.artemis:artemis-odb:${artemisVersion}")
	implementation("org.reflections:reflections:${reflectionsVersion}")
}

val os: OperatingSystem = OperatingSystem.current()

sourceSets.main {
    resources.srcDir("assets")
}

application {
    mainClass.set("Launcher")

    if(os.isMacOsX) {
        applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
    }
}