import node.process.process
import node.fs.writeFile

suspend fun action(
    version: String,
) {
    val gradleUserHome = process.env["GRADLE_USER_HOME"]!!
    writeFile("$gradleUserHome/init.d/extract-publications.init.gradle.kts", extractInitScript(version))
}

/**
 * @param version Workaround because the released action uses a branch strategy. With Immutable Actions, the version can be hard coded via the release process.
 */
// language=Gradle
private fun extractInitScript(version: String) = """beforeSettings {
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
        pluginManager.withPlugin("maven-publish") {
            pluginManager.apply("io.github.hfhbd.extract-publications")
        }
    }
}
"""
