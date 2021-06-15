plugins {
    id("finisterra.java-conventions")
    id("application")
    id("artemis-fluid")
}

// HOTFIX: We can't access the Version Catalog from here unfortunately :(
// So i'll have to weild some desperate hacks for this to work somehow O.O
// Source: https://melix.github.io/blog/#_but_how_can_i_use_the_catalog_in_em_plugins_em_defined_in_code_buildsrc_code
val catalogs = extensions.getByType<VersionCatalogsExtension>()

pluginManager.withPlugin("java-library") {
    val libs = catalogs.named("libs")
	
	// New "hacky" way to declare dependencies
    dependencies.addProvider("implementation", libs.findDependency("artemis-fluid-core").get())
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