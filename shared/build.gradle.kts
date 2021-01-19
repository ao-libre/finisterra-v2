plugins {
    id("finisterra.java-conventions")
}

dependencies {
    api(project(":shared:shared-components"))
    implementation("com.artemis:artemis-odb:0.0.2-SNAPSHOT")
}