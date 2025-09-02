package com.example.codeeditor

import android.content.Context
import kotlinx.serialization.json.Json

@kotlinx.serialization.Serializable
data class SyntaxRules(
    val keywords: List<String>,
    val comments: List<String>,
    val strings: List<String>,
    val types: List<String> = emptyList(),
    val modifiers: List<String> = emptyList()
)

fun loadSyntaxRules(context: Context, filename: String): SyntaxRules {
    val jsonString = context.assets.open(filename).bufferedReader().use { it.readText() }
    return Json.decodeFromString<SyntaxRules>(jsonString)
}

fun getSyntaxRulesForFile(context: Context, filename: String): SyntaxRules {
    return when {
        filename.endsWith(".java") -> loadSyntaxRules(context, "java.json")
        filename.endsWith(".kt") -> loadSyntaxRules(context, "kotlin.json")
        filename.endsWith(".py") -> loadSyntaxRules(context, "python.json")
        filename.endsWith(".c") -> loadSyntaxRules(context, "c.json")
        filename.endsWith(".cpp") -> loadSyntaxRules(context, "cpp.json")
        filename.endsWith(".cc") -> loadSyntaxRules(context, "cpp.json")
        filename.endsWith(".cxx") -> loadSyntaxRules(context, "cpp.json")
        filename.endsWith(".h") -> loadSyntaxRules(context, "c.json")
        filename.endsWith(".hpp") -> loadSyntaxRules(context, "cpp.json")
        else -> loadSyntaxRules(context, "kotlin.json") // default fallback
    }
}
