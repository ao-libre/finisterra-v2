
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
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
    implementation("com.github.jengelman.gradle.plugins:shadow:6.1.0")
    api("gradle.plugin.org.mikeneck:graalvm-native-image-plugin:1.0.0")
    testImplementation("junit:junit:4.13")
}
