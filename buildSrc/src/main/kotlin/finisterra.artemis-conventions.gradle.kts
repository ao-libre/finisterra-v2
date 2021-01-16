plugins {
    id("java-library")
    id("artemis")
    id("artemis-fluid")
}

dependencies {
    implementation("com.artemis:artemis-fluid-core:0.0.2-SNAPSHOT")
}

val fluidOutputDir = file("$buildDir/generated-sources/fluid/")

sourceSets {
    main {
        java.sourceDirectories.plus(fluidOutputDir)
    }
}

tasks {
    create("mkdir") {
        fluidOutputDir.mkdirs()
        outputs.upToDateWhen { false }
    }
    fluid {
        dependsOn("mkdir", "weave")
        classpath = sourceSets.main.get().compileClasspath
        generatedSourcesDirectory = fluidOutputDir
    }

    weave {
        classesDir = sourceSets.main.get().java.outputDir
        isEnableArtemisPlugin = true
        isEnablePooledWeaving = true
        isGenerateLinkMutators = true
        isOptimizeEntitySystems = true
    }

    getByName("classes").finalizedBy("weave")
    getByName("compileJava").dependsOn("fluid")
}