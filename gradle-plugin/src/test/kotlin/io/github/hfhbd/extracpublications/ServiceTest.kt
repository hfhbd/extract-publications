package io.github.hfhbd.extracpublications

import io.github.hfhbd.extractpublications.service.WriteLockService
import org.gradle.kotlin.dsl.registerIfAbsent
import org.gradle.testfixtures.ProjectBuilder
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class ServiceTest {
    @Test
    fun testService() {
        val project = ProjectBuilder.builder().build()

        val file = File.createTempFile("foo", "bar")

        val service = project.gradle.sharedServices.registerIfAbsent("myService", WriteLockService::class) {
            parameters {
                outputFile.set(file)
            }
        }

        service.get().use {
            it.addFileToOutput("asdf")
            it.addFileToOutput("bar")
        }

        assertEquals("publishedFiles=asdf,bar,", file.readText())
    }
}
