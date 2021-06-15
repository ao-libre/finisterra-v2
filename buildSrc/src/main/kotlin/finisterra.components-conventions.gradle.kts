plugins {
    id("finisterra.java-conventions")
    id("artemis")
}

// HOTFIX: We can't access the Version Catalog from here unfortunately :(
// So i'll have to weild some desperate hacks for this to work somehow O.O
// Source: https://melix.github.io/blog/#_but_how_can_i_use_the_catalog_in_em_plugins_em_defined_in_code_buildsrc_code
val catalogs = extensions.getByType<VersionCatalogsExtension>()

pluginManager.withPlugin("java-library") {
    val libs = catalogs.named("libs")
	
	// New "hacky" way to declare dependencies
    dependencies.addProvider("implementation", libs.findDependency("artemis-core").get())
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
