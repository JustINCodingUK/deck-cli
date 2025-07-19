package dev.justincodinguk.deckcli.project

class ScriptNotFoundException(scriptName: String, projectName: String) :
    Exception("Script $scriptName not found in project $projectName")