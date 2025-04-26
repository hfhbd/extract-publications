import io.github.hfhbd.extractpublications.service.WriteLockService
import io.github.hfhbd.extractpublications.tasks.WritePublicationsToGitHubOutputFile

plugins {
    id("maven-publish")
}

gradle.sharedServices.registerIfAbsent("writeLock", WriteLockService::class) {
    parameters.outputFile.set(file(providers.environmentVariable("GITHUB_OUTPUT")))
}

val mavenArtefacts = provider {
    val s = objects.fileCollection()

    for (mavenArtefact in publishing.publications.withType<MavenPublication>().flatMap { it.artifacts }) {
        s.from(mavenArtefact.file).builtBy(mavenArtefact.buildDependencies)
    }
    s
}

tasks.register("writePublicationsToGitHubOutputFile", WritePublicationsToGitHubOutputFile::class) {
    publicationFiles.from(mavenArtefacts)
}
