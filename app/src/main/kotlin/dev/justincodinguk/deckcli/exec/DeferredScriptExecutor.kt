package dev.justincodinguk.deckcli.exec

import dev.justincodinguk.devdeck.core.deck_api.DeckFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

class DeferredScriptExecutor(
    private val deckFile: DeckFile
) {

    private val logger = LoggerFactory.getLogger("DeferredScriptExecutor")

    companion object {
        suspend fun execute(deckFile: DeckFile) {
            val executor = DeferredScriptExecutor(deckFile)
            executor.run()
        }
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val job = coroutineScope.launch(start = CoroutineStart.LAZY) {
        val taskHandler = deckFile.taskHandler
        logger.info("Executing ${deckFile.name}")
        deckFile.tasks.forEach {
            logger.info("${it.javaClass.name}: ${it.name}")
        }
        deckFile.execute()
        while (taskHandler.isActive()) { delay(1000) }
    }

    suspend fun run() {
        job.apply {
            start()
            logger.info("Job started")
            join()
            logger.info("Job finished")
        }
    }
}