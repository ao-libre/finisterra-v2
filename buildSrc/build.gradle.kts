
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
    maven {
        url = uri("https://maven.pkg.github.com/guidota/artemis-odb")
        credentials {
            username = "token"
            password = "19639b43e2c7027d5" + "5508183e0c5ae105c471ad8"
        }
    }
}

dependencies {
    implementation("com.artemis:artemis-fluid-gradle-plugin:0.0.2-SNAPSHOT")
    implementation("com.artemis:artemis-odb-gradle-plugin:0.0.2-SNAPSHOT")
    implementation("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.0.5")
    testImplementation("junit:junit:4.13")
}
