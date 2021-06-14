plugins {
    id("finisterra.artemis-conventions")
}

val artemisVersion: String by project
val artemisContribVersion: String by project
val junitVersion: String by project
val kryonetVersion: String by project

dependencies {
    api(projects.shared)
    implementation(projects.server.components)
	
    implementation("com.github.crykn:kryonet:$kryonetVersion")
    implementation("com.artemis:artemis-odb:$artemisVersion")
    implementation("net.mostlyoriginal.artemis-odb:contrib-eventbus:$artemisContribVersion")
    
	testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application {
    @Suppress("DEPRECATION") // it will be deprecated in gradle 8 but shadow jar still not supports `mainClass`
    mainClassName = "Launcher"
    mainClass.set("Launcher")
}

tasks.test {
    useJUnitPlatform()
}