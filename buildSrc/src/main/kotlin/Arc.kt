import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun getArcHash(): String = "25dcd2f7664f3c5a669447fdf8dffaea7e65fe0d"

fun DependencyHandler.arcModule(name: String): Dependency =
    create("com.github.Anuken.Arc:$name:${getArcHash()}")

fun DependencyHandler.arcImplementation(name: String): Dependency? =
    add(
        "implementation",
        "com.github.Anuken.Arc:${if (name.contains(":")) name.split(':').last() else name}:${getArcHash()}"
    )

fun DependencyHandler.arcApi(name: String): Dependency? =
    add("implementation", "com.github.Anuken.Arc:$name:${getArcHash()}")