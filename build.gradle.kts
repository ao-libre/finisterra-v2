allprojects {
    group = "com.finisterra"
    version = "0.0.1-SNAPSHOT"
    repositories {
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/guidota/artemis-odb")
            credentials {
                username = "token"
                password = "19639b43e2c7027d5" + "5508183e0c5ae105c471ad8"
            }
        }
    }
}