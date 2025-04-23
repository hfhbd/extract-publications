import node.process.process
import node.fs.writeFile

suspend fun action(
    version: String,
) {
    val gradleUserHome = process.env["GRADLE_USER_HOME"]!!
    writeFile("$gradleUserHome/init.d/extract-publications.init.gradle.kts", extractInitScript(version))
}

// language=Gradle
private fun extractInitScript(version: String) = """
    beforeSettings {
      buildscript.repositories {
        maven(url = "https://maven.pkg.github.com/hfhbd/extract-publications") {
            name = "GitHubPackages"
            credentials(PasswordCredentials::class)
        }

    }
    buildscript.dependencies.add("classpath", "io.github.hfhbd.extract-publications:gradle-plugin:$version")
}

lifecycle.afterProject {
    if (isolated.buildTreePath == ":") {
        if (this == rootProject) {
            pluginManager.apply("io.github.hfhbd.extract-publications.root")
        }

        pluginManager.withPlugin("maven-publish") {
            pluginManager.apply("io.github.hfhbd.extract-publications")
        }
    }
}

"""
