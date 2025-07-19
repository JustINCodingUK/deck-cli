package dev.justincodinguk.deckcli.project

class InvalidDeckProjectException(projectPath: String) : Exception("Invalid Deck Project at $projectPath")