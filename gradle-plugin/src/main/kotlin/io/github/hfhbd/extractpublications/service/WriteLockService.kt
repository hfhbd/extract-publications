package io.github.hfhbd.extractpublications.service

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import javax.inject.Inject

abstract class WriteLockService @Inject constructor(
) : BuildService<WriteLockService.Param>, AutoCloseable {
    interface Param : BuildServiceParameters {
        val outputFile: RegularFileProperty
    }

    private val writer = parameters.outputFile.asFile.get().bufferedWriter()

    init {
        writer.appendLine("publishedFiles<<EOF")
    }

    fun appendLine(text: CharSequence) {
        writer.appendLine(text)
    }

    override fun close() {
        writer.write("EOF")
        writer.close()
    }
}
