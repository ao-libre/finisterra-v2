plugins {
    id("finisterra.java-conventions")
    id("application")
    id("artemis-fluid")
}

dependencies {
    implementation("com.artemis:artemis-fluid-core:0.0.3-SNAPSHOT")
}

val fluidOutputDir = file("${buildDir}/generated-sources/fluid/")

sourceSets {
    main {
        java {
            srcDirs(fluidOutputDir)
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
        preferences.excludeFromClasspath = listOf("kryo-")
    }

    getByName("compileJava").dependsOn("fluid")
}