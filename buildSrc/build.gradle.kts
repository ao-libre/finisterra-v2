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
            password = "ghp_yZ88NIuG96I9sxo" + "h4w11Wyuhtfv04k1uGRNr"
        }
    }
}

dependencies {
	implementation(libs.artemis.gradlePlugin)
    implementation(libs.artemis.fluid.core)
	implementation(libs.artemis.fluid.gradlePlugin)
}
