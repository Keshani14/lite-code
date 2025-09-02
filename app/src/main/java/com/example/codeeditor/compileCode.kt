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
    val syntaxErrorHandler = SyntaxErrorHandler()
    val syntaxErrors = syntaxErrorHandler.checkSyntaxErrors(code, fileName)
    
    if (syntaxErrors.isNotEmpty()) {
        Log.d("compileCode", "Syntax errors found: ${syntaxErrors.size}")
        // Build error message
        val errorMessage = buildString {
            append("Compilation failed due to syntax errors:\n\n")
            syntaxErrors.forEach { error ->
                append("Line ${error.lineNumber}: ${error.message}\n")
            }
            append("\nPlease fix these errors before compiling.")
        }
        onResult(errorMessage)
        return
    }
    
    Log.d("compileCode", "No syntax errors found, proceeding with file save")
    
    // 1. Save the file internally (no syntax errors found)
    Log.d("compileCode", "Saving file internally...")
    fileManager.saveFile(fileName, code)
    val internalFile = File(context.filesDir, fileName)
    val externalDir = context.getExternalFilesDir(null) // app-specific external folder
    
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
        if (internalFile.exists()) {
            Log.d("compileCode", "Copying internal file to external location...")
            internalFile.copyTo(externalFile, overwrite = true)
            Log.d("compileCode", "File copied successfully")
        } else {
            Log.e("compileCode", "Internal file does not exist")
            onResult("Error: Internal file not found")
            return
        }
    } catch (e: Exception) {
        Log.e("compileCode", "Error copying file: ${e.message}", e)
        onResult("Error: Failed to save file to external storage. ${e.message}")
        return
    }

    // 2. Show success message via onResult
    Log.d("compileCode", "Compilation completed successfully")
    val instructions = "File saved successfully at $externalFile "

//          On your PC, do the following manually:

//        1. Push the file to the device:
//            adb shell
//            cd  $externalFile
//            ls
//            exit

//        2. Push the file to the device:
//           adb pull $externalFile C:/Users/<Device name>/Desktop/file name
//        3. Compile it using your script:
//           ./compile_kotlin.sh /sdcard/$fileName

    onResult(instructions)
}
