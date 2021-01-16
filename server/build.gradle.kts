plugins {
    id("finisterra.java-conventions")
    id("finisterra.artemis-conventions")
}

dependencies {
    implementation(project(":server:components"))
    implementation(project(":shared:shared-components"))
    implementation("com.artemis:artemis-odb:0.0.2-SNAPSHOT")
}


