plugins {
    id("finisterra.artemis-conventions")
    id("java-library")
}

dependencies {
    implementation(project(":server:components"))
    api(project(":shared"))
    implementation("com.artemis:artemis-odb:0.0.2-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
}


