allprojects {
    group = "com.finisterra"
    version = "0.0.1-SNAPSHOT"
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/guidota/artemis-odb")
            credentials {
                username = "token"
                password = "87b9a2582516f1893d0182f3cda3ab009673ff3b"
            }
        }
    }
}