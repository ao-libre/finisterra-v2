import org.gradle.internal.os.OperatingSystem

plugins {
    id("org.mikeneck.graalvm-native-image")
}

val os: OperatingSystem = OperatingSystem.current()

generateNativeImageConfig {
    enabled = true
    byRunningApplication {
        if(os.isMacOsX) {
            jvmArguments("-XstartOnFirstThread")
        }
        arguments("once")
    }
}

nativeImage {
    dependsOn("generateNativeImageConfig")
    graalVmHome = System.getenv("JAVA_HOME")
    mainClass = "Launcher"
    executableName = rootProject.name + "-" + project.name
    arguments {
        if(os.isMacOsX) {
            add("-J-XstartOnFirstThread")
        }
        add( "--no-fallback")
        add( "--enable-all-security-services")
        add( "--report-unsupported-elements-at-runtime")
        add( "-H:ConfigurationResourceRoots=native-image/")
        add( "-H:+PrintClassInitialization")
        add( "-H:+ReportExceptionStackTraces")
        add( "-H:GenerateDebugInfo=1")
        add( "-H:+AllowVMInspection")
        add( "--native-image-info")
        add( "--verbose")
        add( "--trace-class-initialization=org.lwjgl.*")
//        add( "--initialize-at-run-time=org.lwjgl,org.lwjgl.system,org.lwjgl.opengl,com.badlogic.gdx.backends.lwjgl3,org.lwjgl.system.MemoryUtil,org.lwjgl.opengl.GL$1,org.lwjgl.opengl.GL$2,org.lwjgl.opengl.GL$3,org.lwjgl.opengl.GL$ICD,org.lwjgl.opengl.GLCapabilities,org.lwjgl.opengl.GLUtil,org.lwjgl.openal.AL,org.lwjgl.openal.ALC,org.lwjgl.openal.AL$ICDStatic,org.lwjgl.openal.AL$ICDStatic$WriteOnce,org.lwjgl.opengl.NVTextureBarrier,org.lwjgl.opengl.NVShaderBufferLoad,org.lwjgl.opengl.NVSampleLocations,org.lwjgl.opengl.NVQueryResourceTag,org.lwjgl.system.macosx.CoreFoundation,org.lwjgl.system.windows.WindowsLibrary,org.lwjgl.openal.AL$WriteOnce,org.lwjgl.system.ThreadLocalUtil,com.badlogic.gdx.backends.lwjgl3.audio.OpenALAudio,org.lwjgl.opengl.GL,org.lwjgl.opengl.GL$SharedLibraryGL,org.lwjgl.system.Callback,org.lwjgl.system.Callback$B,org.lwjgl.system.Callback$D,org.lwjgl.system.Callback$F,org.lwjgl.system.Callback$I,org.lwjgl.system.Callback$J,org.lwjgl.system.Callback$N,org.lwjgl.system.Callback$P,org.lwjgl.system.Callback$S,org.lwjgl.system.Callback$V,org.lwjgl.system.Callback$Z"
        add( "--initialize-at-run-time=org.lwjgl,org.lwjgl.glfw,org.lwjgl.opengl,org.lwjgl.system,org.lwjgl.opengl.GL\$SharedLibraryGL,org.lwjgl.system.MemoryUtil\$MemoryAllocator,org.lwjgl.system.SharedLibraryLoader,org.lwjgl.system.SharedLibrary,com.badlogic.gdx.backends.lwjgl3,com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window,org.lwjgl.system.MemoryUtil$1,org.lwjgl.system.MemoryUtil$2,org.lwjgl.system.MemoryManage")
    }
}

tasks.named<org.mikeneck.graalvm.DefaultMergeNativeImageConfigTask>("mergeNativeImageConfig") {
    destinationDir.set(file("src/main/resources/native-image"))
}