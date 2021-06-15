import org.gradle.internal.os.OperatingSystem

plugins {
    id("finisterra.artemis-conventions")
}

dependencies {
	implementation(projects.client.components)
	implementation(projects.shared)
	
    implementation(libs.gdx.backend)
	implementation(variantOf(libs.gdx.platform) { classifier("natives-desktop") })
	implementation(libs.gdx.freetype.core)
	implementation(variantOf(libs.gdx.freetype.platform) { classifier("natives-desktop") })
	
	
	implementation(libs.artemis.core)
	implementation(libs.reflections)
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