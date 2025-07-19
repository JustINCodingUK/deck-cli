package dev.justincodinguk.deckcli.project

import dev.justincodinguk.devdeck.core.deck_api.DeckFile
import dev.justincodinguk.devdeck.core.deck_api.deckFileCompiler
import java.io.File

class DeckProject private constructor(
    val projectName: String,
    val scripts: List<DeckFile>
) {

    companion object {

        fun load(path: String): DeckProject {
            val scripts = mutableListOf<DeckFile>()
            for(file in File("$path/.deck/scripts").listFiles()!!) {
                val compiler = deckFileCompiler {
                    fileName = file.name.removeSuffix(".deckfile")
                    filePath = file.absolutePath
                    // TODO: Extract the references to .devdeck from the app

                    taskBatchSize = 5
                }
                scripts.add(compiler.loadDeckFile())
            }
            val projectName = File(path).name
            if(scripts.isEmpty()) throw InvalidDeckProjectException(path)
            return DeckProject(projectName, scripts)
        }

    }

    fun loadScript(name: String): DeckFile {
        val script = scripts.find { it.name == name }
        return script ?: throw ScriptNotFoundException(name, projectName)
    }
}