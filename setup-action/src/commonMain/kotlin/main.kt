import node.process.process
import io.github.hfhbd.extractpublications.VERSION
import node.fs.writeFile

suspend fun action() {
    val gradleUserHome = process.env["GRADLE_USER_HOME"]!!
    writeFile("$gradleUserHome/init.d/extract-publications.init.gradle.kts", extractInitScript)
}

// language=Gradle
private const val extractInitScript = """
    initscript {
        repositories {
            maven(url = "https://maven.pkg.github.com/hfhbd/extract-publications") {
                name = "GitHubPackages"
                credentials(PasswordCredentials::class)
            }
        }

        dependencies {
            classpath("io.github.hfhbd.extract-publications:gradle-plugin:$VERSION")
        }
    }

    lifecycle.afterProject {
        if (this == rootProject) {
            pluginManager.apply("io.github.hfhbd.extract-publications.root")
        }

        pluginManager.withPlugin("maven-publish") {
            pluginManager.apply("io.github.hfhbd.extract-publications")
        }
    }

"""
