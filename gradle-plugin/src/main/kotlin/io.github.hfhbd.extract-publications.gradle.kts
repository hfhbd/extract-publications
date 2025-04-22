plugins {
    id("maven-publish")
}

configurations.consumable("githubPublications") {
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named("GITHUB_OUTPUT"))
    }
    outgoing {
        artifacts(provider {
            publishing.publications.withType<MavenPublication>().flatMap {
                it.artifacts
            }.map {
                it.file
            }
        })
    }
}
