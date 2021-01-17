import org.gradle.internal.os.OperatingSystem

plugins {
    id("org.mikeneck.graalvm-native-image")
    id("finisterra.shadow-conventions")
}

val os: OperatingSystem = OperatingSystem.current()

generateNativeImageConfig {
    enabled = true
    byRunningApplication {
        arguments("once")
        if(os.isMacOsX) {
            arguments("-XstartOnFirstThread")
        }
    }
}

nativeImage {
    graalVmHome = System.getenv("JAVA_HOME")
    mainClass = "Launcher"
    executableName = "finisterra-" + project.name
}

tasks.named<org.mikeneck.graalvm.DefaultMergeNativeImageConfigTask>("mergeNativeImageConfig") {
    destinationDir.set(file("src/main/resources/native-image"))
}