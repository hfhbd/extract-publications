import io.github.hfhbd.extractpublications.tasks.WritePublicationsToGitHubOutputFile

val publications = configurations.dependencyScope("publications")
val subPublicationFiles = configurations.resolvable("publicationFiles") {
    extendsFrom(publications.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named("GITHUB_OUTPUT"))
    }
}

tasks.register("writePublicationsToGithubOutputFile", WritePublicationsToGitHubOutputFile::class) {
    publicationFiles.from(subPublicationFiles)
    githubOutputFile.set(file(providers.environmentVariable("GITHUB_OUTPUT")))
}
