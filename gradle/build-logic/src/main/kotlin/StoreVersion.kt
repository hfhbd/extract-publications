import org.gradle.api.*
import org.gradle.api.file.*
import org.gradle.api.provider.*
import org.gradle.api.tasks.*
import java.io.*

@CacheableTask
abstract class StoreVersion : DefaultTask() {
    @get:Input
    abstract val projectVersion: Property<String>

    init {
        projectVersion.convention(project.version.toString())
    }

    @get:Input
    abstract val projectGroup: Property<String>

    init {
        projectGroup.convention(project.group.toString())
    }

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    init {
        outputDirectory.convention(
            project.layout.buildDirectory.dir(
                projectGroup.map {
                    "generated/" + it.replace(".", "/").replace("-", "")
                }
            )
        )
    }

    @TaskAction
    fun action() {
        File(outputDirectory.get().asFile, "Version.kt").writeText(
            """
            |package ${projectGroup.get().replace("-", "")}
            |
            |internal const val VERSION: String = "${projectVersion.get()}"
            |
            """.trimMargin(),
        )
    }
}
