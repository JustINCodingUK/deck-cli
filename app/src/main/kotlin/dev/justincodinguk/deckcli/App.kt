package dev.justincodinguk.deckcli

import dev.justincodinguk.deckcli.exec.DeferredScriptExecutor
import dev.justincodinguk.deckcli.project.DeckProject
import dev.justincodinguk.deckcli.project.InvalidDeckProjectException
import dev.justincodinguk.deckcli.util.ResourceManager
import dev.justincodinguk.devdeck.core.deck_api.deckFileCompiler
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.io.File

fun main(args: Array<String>) = runBlocking {

    val logger = LoggerFactory.getLogger("Deck CLI")

    when (args.first()) {
        "project" -> {
            val project = try {
                val loadedProject = DeckProject.load(System.getProperty("user.dir"))
                logger.info("Project ${loadedProject.projectName} with ${loadedProject.scripts.size} scripts loaded")
                loadedProject.scripts.forEach { logger.info(" - ${it.name}") }
                loadedProject
            } catch (e: InvalidDeckProjectException) {
                logger.error(e.message)
                return@runBlocking
            }
            DeferredScriptExecutor.execute(project.loadScript(args[1]))
        }

        "--setup" -> {
            val resourceManager = ResourceManager()
            resourceManager.extractResources()
        }

        else -> {
            val compiler = deckFileCompiler {
                filePath = args[0]
                referencesDirectory = System.getProperty("user.home")+"/.devdeck/install_refs/"
                taskBatchSize = 5
            }
            val deckFile = compiler.loadDeckFile()
            val fileName = if (deckFile.name == "Unspecified") {
                args[0].replace("\\", "/").split("/").last().removeSuffix(".deckfile")
            } else deckFile.name
            logger.info("Applying $fileName to current environment")
            DeferredScriptExecutor.execute(deckFile)
        }
    }

    logger.info("Exiting")
}

