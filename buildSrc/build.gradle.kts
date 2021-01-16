
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    maven {
        url = uri("https://maven.pkg.github.com/guidota/artemis-odb")
        credentials {
            username = "token"
            password = "87b9a2582516f1893d0182f3cda3ab009673ff3b"
        }
    }
}

dependencies {
    implementation("com.artemis:artemis-fluid-gradle-plugin:0.0.2-SNAPSHOT")
    implementation("com.artemis:artemis-odb-gradle-plugin:0.0.2-SNAPSHOT")
    implementation("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.0.5")
    testImplementation("junit:junit:4.13")
}
