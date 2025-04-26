import io.github.hfhbd.extractpublications.service.WriteLockService
import io.github.hfhbd.extractpublications.tasks.WritePublicationsToGitHubOutputFile

plugins {
    id("maven-publish")
}

gradle.sharedServices.registerIfAbsent("writeLock", WriteLockService::class) {
    parameters.outputFile.set(file(providers.environmentVariable("GITHUB_OUTPUT")))
}

val mavenArtefacts = objects.fileCollection()
for (mavenArtefact in publishing.publications.withType<MavenPublication>().flatMap { it.artifacts }) {
    mavenArtefacts.from(mavenArtefact.file)
    mavenArtefacts.builtBy(mavenArtefact.buildDependencies)
}

tasks.register("writePublicationsToGitHubOutputFile", WritePublicationsToGitHubOutputFile::class) {
    publicationFiles.from(mavenArtefacts)
}
