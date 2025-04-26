package io.github.hfhbd.extracpublications

import Io_github_hfhbd_extractPublicationsPlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

class TaskTest {
    @Test
    fun testServiceInjection() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(Io_github_hfhbd_extractPublicationsPlugin::class)

        project.tasks.getByName("writePublicationsToGitHubOutputFile")
    }
}
