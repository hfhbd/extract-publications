package io.github.hfhbd.extractpublications.tasks

import io.github.hfhbd.extractpublications.service.WriteLockService
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.Provider
import org.gradle.api.services.ServiceReference
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault
abstract class WritePublicationsToGitHubOutputFile : DefaultTask() {

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.ABSOLUTE)
    abstract val publicationFiles: ConfigurableFileCollection

    @get:ServiceReference("writeLock")
    abstract val writeLockService: Provider<WriteLockService>

    @TaskAction
    internal fun action() {
        val writeLockService = writeLockService.get()
        for (file in publicationFiles) {
            writeLockService.appendLine(file.absolutePath)
        }
    }
}
