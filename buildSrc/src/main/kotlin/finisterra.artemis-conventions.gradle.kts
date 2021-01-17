plugins {
    id("finisterra.java-conventions")
    id("application")
    id("artemis")
    id("artemis-fluid")
}

dependencies {
    implementation("com.artemis:artemis-fluid-core:0.0.2-SNAPSHOT")
}

val fluidOutputDir = file("${buildDir}/generated-sources/fluid/")
val componentsDir = file("${projectDir}/components/src/main/java")
val sharedComponentsDir = file("${rootDir}/shared/components/src/main/java")

sourceSets {
    main {
        java {
            srcDirs(fluidOutputDir, componentsDir, sharedComponentsDir)
        }
    }
}

tasks {
    create("mkdir") {
        fluidOutputDir.mkdirs()
        outputs.upToDateWhen { false }
    }
    fluid {
        dependsOn("mkdir")
        classpath = sourceSets.main.get().compileClasspath
        generatedSourcesDirectory = fluidOutputDir
    }

    weave {
        dependsOn("build")
        classesDirs = files(sourceSets.main.get().java.outputDir)
        isEnableArtemisPlugin = true
        isEnablePooledWeaving = true
        isGenerateLinkMutators = true
        isOptimizeEntitySystems = true
    }

    getByName("classes").finalizedBy("weave")
    getByName("compileJava").dependsOn("fluid")
}