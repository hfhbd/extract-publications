plugins {
    id("app.softwork.kotlin.actions")
}

kotlin.sourceSets.commonMain {
    kotlin {
        srcDir(tasks.register("generateVersion", StoreVersion::class))
    }
}

plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin> {
    the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec>().downloadBaseUrl.set(null)
}
