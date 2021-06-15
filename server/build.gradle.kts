plugins {
    id("finisterra.artemis-conventions")
}

dependencies {
    api(projects.shared)
    implementation(projects.server.components)
	
    implementation(libs.kryonet)
    implementation(libs.artemis.core)
    implementation(libs.artemis.contrib.eventbus)
    
	testImplementation(libs.jupiter.api)
    testRuntimeOnly(libs.jupiter.engine)
}

application {
    mainClass.set("Launcher")
}

tasks.test {
    useJUnitPlatform()
}