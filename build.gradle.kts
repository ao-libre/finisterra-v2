allprojects {
    group = "com.finisterra"
    version = "0.0.1-SNAPSHOT"
    repositories {
        maven { url = uri("https://jitpack.io") }
        maven {
            url = uri("https://maven.pkg.github.com/guidota/artemis-odb")
            credentials {
                username = "token"
                password = "ghp_yZ88NIuG96I9sxo" + "h4w11Wyuhtfv04k1uGRNr"
            }
        }
    }
}