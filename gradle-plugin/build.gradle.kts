plugins {
    `kotlin-dsl`
    id("publish")
}

kotlin.jvmToolchain(21)

testing.suites.withType<JvmTestSuite>().configureEach {
    useKotlinTest()
}

gradlePlugin.plugins.configureEach {
    displayName = "Gradle plugin to write publications to GitHub OUTPUT"
    description = "Gradle plugin to write publications to GitHub OUTPUT"
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
