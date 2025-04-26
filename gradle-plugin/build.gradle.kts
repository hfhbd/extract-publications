plugins {
    `kotlin-dsl`
    id("publish")
}

kotlin.jvmToolchain(21)

java {
    withSourcesJar()
    withJavadocJar()
}

testing.suites.withType<JvmTestSuite>().configureEach {
    useKotlinTest()
    targets.configureEach {
        testTask {
            environment("GITHUB_OUTPUT", "foo")
        }
    }
}

gradlePlugin.plugins.configureEach {
    displayName = "Gradle plugin to write publications to GITHUB_OUTPUT"
    description = "Gradle plugin to write publications to GITHUB_OUTPUT"
}

configurations.configureEach {
    if (isCanBeConsumed) {
        attributes {
            attribute(
                GradlePluginApiVersion.GRADLE_PLUGIN_API_VERSION_ATTRIBUTE,
                objects.named(GradleVersion.version("9.0").version)
            )
        }
    }
}
