import org.gradle.internal.os.OperatingSystem

plugins {
    id("org.mikeneck.graalvm-native-image")
}

val os: OperatingSystem = OperatingSystem.current()

generateNativeImageConfig {
    enabled = true
    byRunningApplication {
        arguments("once")
        if (os.isMacOsX) {
            jvmArguments("-XstartOnFirstThread")
        }
    }
}

tasks.named<org.mikeneck.graalvm.DefaultMergeNativeImageConfigTask>("mergeNativeImageConfig") {
    destinationDir.set(file("src/main/resources/native-image"))
}

nativeImage {
    dependsOn("generateNativeImageConfig")
    graalVmHome = System.getenv("JAVA_HOME")
    mainClass = "Launcher"
    executableName = rootProject.name + "-" + project.name
    arguments {
        if (os.isMacOsX) {
            add("-J-XstartOnFirstThread")
        }
        add("--no-fallback")
        add("--enable-all-security-services")
        add("--report-unsupported-elements-at-runtime")
        add("-H:ConfigurationResourceRoots=native-image/")
        add("-H:+PrintClassInitialization")
        add("-H:+ReportExceptionStackTraces")
        add("-H:GenerateDebugInfo=1")
        add("-H:+AllowVMInspection")
        add("--native-image-info")
        add("--verbose")
    }
}
