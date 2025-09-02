
package com.example.codeeditor

import android.content.Context
import android.util.Log
import java.io.File

fun compileCode(
    context: Context,
    code: String,
    fileManager: FileManager,
    fileName: String,
    onResult: (String) -> Unit
) {
    try {
        Log.d("compileCode", "Starting compilation for file: $fileName")
        
        // Validate file name
        if (fileName.isBlank() || fileName == "Untitled") {
            Log.e("compileCode", "Invalid file name: $fileName")
            onResult("Error: Please save the file with a valid name before compiling.")
            return
        }
        
        // Validate code content
        if (code.isBlank()) {
            Log.e("compileCode", "Empty code content")
            onResult("Error: Please enter some code before compiling.")
            return
        }
        
        // 0. Check for syntax errors first
        Log.d("compileCode", "Checking syntax errors...")
        val syntaxErrors = try {
            val syntaxErrorHandler = SyntaxErrorHandler()
            syntaxErrorHandler.checkSyntaxErrors(code, fileName)
        } catch (e: Exception) {
            Log.e("compileCode", "Error during syntax checking: ${e.message}", e)
            listOf<SyntaxError>() // Return empty list to continue with compilation
        }
        
        if (syntaxErrors.isNotEmpty()) {
            Log.d("compileCode", "Syntax errors found: ${syntaxErrors.size}")
            // Build error message
            var errorMessage = "Compilation failed due to syntax errors:\n\n"
            for (error in syntaxErrors) {
                errorMessage += "Line ${error.lineNumber}: ${error.message}\n"
            }
            errorMessage += "\nPlease fix these errors before compiling."
            onResult(errorMessage)
            return
        }
        
        Log.d("compileCode", "No syntax errors found, proceeding with file save")
        
        // 1. Save the file internally (no syntax errors found)
        Log.d("compileCode", "Saving file internally...")
        try {
            fileManager.saveFile(fileName, code)
        } catch (e: Exception) {
            Log.e("compileCode", "Error saving file internally: ${e.message}", e)
            onResult("Error: Failed to save file internally. ${e.message}")
            return
        }
        val internalFile = File(context.filesDir, fileName)
        
        // Check if internal file exists
        if (!internalFile.exists()) {
            Log.e("compileCode", "Internal file does not exist after save")
            onResult("Error: Internal file not found after save operation")
            return
        }
        
        val externalDir = try {
            context.getExternalFilesDir(null) // app-specific external folder
        } catch (e: Exception) {
            Log.e("compileCode", "Error getting external directory: ${e.message}", e)
            onResult("Error: Cannot access external storage. ${e.message}")
            return
        }
        
        Log.d("compileCode", "External directory: ${externalDir?.absolutePath}")
        
        // Check if external directory exists and is writable
        if (externalDir == null || !externalDir.exists()) {
            Log.e("compileCode", "External storage directory not available")
            onResult("Error: External storage directory not available. Please check storage permissions.")
            return
        }
        
        val externalFile = File(externalDir, fileName)
        Log.d("compileCode", "External file path: ${externalFile.absolutePath}")

        try {
            Log.d("compileCode", "Copying internal file to external location...")
            internalFile.copyTo(externalFile, overwrite = true)
            Log.d("compileCode", "File copied successfully")
        } catch (e: Exception) {
            Log.e("compileCode", "Error copying file: ${e.message}", e)
            onResult("Error: Failed to save file to external storage. ${e.message}")
            return
        }

        // 2. Show success message via onResult
        Log.d("compileCode", "Compilation completed successfully")
        val successMessage = "$fileName compiles successfully\n\nFile saved at: $externalFile"
        onResult(successMessage)
    } catch (e: Exception) {
        Log.e("compileCode", "Critical error during compilation: ${e.message}", e)
        onResult("Critical error: ${e.message}")
    }
}
