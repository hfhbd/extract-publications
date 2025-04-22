plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.plugins.kotlin.actions.dep)
}

val Provider<PluginDependency>.dep: Provider<String> get() = map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

tasks.validatePlugins {
    enableStricterValidation.set(true)
}
