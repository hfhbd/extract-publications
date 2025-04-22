package io.github.hfhbd.extractpublications.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault
abstract class WritePublicationsToGitHubOutputFile : DefaultTask() {

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.ABSOLUTE)
    abstract val publicationFiles: ConfigurableFileCollection

    @get:OutputFile
    abstract val githubOutputFile: RegularFileProperty

    @TaskAction
    internal fun action() {
        githubOutputFile.get().asFile.appendText(
            publicationFiles.joinToString(
                prefix = "publishedFiles<<EOF\n",
                separator = "\n",
                postfix = "\nEOF\n",
            ) {
                it.absolutePath
            }
        )
    }
}
