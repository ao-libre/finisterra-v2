plugins {
    id("finisterra.java-conventions")
    id("artemis")
}

dependencies {
    implementation("com.artemis:artemis-odb:0.0.3-SNAPSHOT")
}

tasks {
    weave {
        dependsOn("build")
        classesDirs = files(sourceSets.main.get().java.classesDirectory)
        isEnableArtemisPlugin = true
        isEnablePooledWeaving = true
        isGenerateLinkMutators = true
        isOptimizeEntitySystems = true
    }
    getByName("classes").finalizedBy("weave")
}
