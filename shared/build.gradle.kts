plugins {
    id("finisterra.java-conventions")
}

val artemisVersion: String by project

dependencies {
    api(projects.shared.sharedComponents)
	
    implementation(libs.artemis.core)
}