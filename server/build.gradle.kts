plugins {
    id("finisterra.artemis-conventions")
    id("finisterra.native-conventions")
}

dependencies {
    implementation(project(":server:components"))
    api(project(":shared"))
    implementation("com.artemis:artemis-odb:0.0.2-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

application {
    @Suppress("DEPRECATION") // it will be deprecated in gradle 8 but shadow jar still not supports `mainClass`
    mainClassName = "Launcher"
    mainClass.set("Launcher")
}

tasks.test {
    useJUnitPlatform()
}
