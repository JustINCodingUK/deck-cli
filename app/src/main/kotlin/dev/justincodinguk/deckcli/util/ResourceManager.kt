package dev.justincodinguk.deckcli.util

import org.slf4j.LoggerFactory
import java.io.File

class ResourceManager {

    private val logger = LoggerFactory.getLogger("ResourceManager")

    fun extractResources() {
        val manifestStream = javaClass.getResourceAsStream("/res_manifest.txt")
        requireNotNull(manifestStream) { "Manifest not found!" }

        val resourcePaths = manifestStream.bufferedReader().readLines()
        val extractionPath = System.getProperty("user.home")+"/.devdeck/install_refs/"

        for (path in resourcePaths) {
            val input = requireNotNull(javaClass.getResourceAsStream("/$path"))
            val fileExtractionPath = path.split("install_refs/").last()
            File(extractionPath+fileExtractionPath.split("/").first()).mkdirs()
            File(extractionPath + fileExtractionPath).apply { createNewFile() }.outputStream().use { output ->
                input.copyTo(output)
            }
        }
    }
}