plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven { url = uri("https://jitpack.io") }
    maven {
        url = uri("https://maven.pkg.github.com/guidota/artemis-odb")
        credentials {
            username = "token"
            password = "19639b43e2c7027d55508183e0c5ae105c471ad8"
        }
    }
}

dependencies {
	implementation(libs.artemis.gradlePlugin)
    implementation(libs.artemis.fluid.core)
	implementation(libs.artemis.fluid.gradlePlugin)
}
