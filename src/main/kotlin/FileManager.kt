import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths

class FileManager {
    fun writeToFile(path: String, text: String): Boolean {
        val file = File(path)

        file.createNewFile()

        try {
            PrintWriter(file, Charsets.UTF_8).use { it.print(text) }
        } catch (e: IOException) {
            return false
        }
        return true
    }

    fun readFromFile(path: String): String {
        val file = File(path)
        if (!file.exists() || file.isDirectory) {
            throw FileNotFoundException("This file does not exist")
        }

        val paths = Paths.get(path)
        var result = String()

        try {
            Files.readAllLines(paths, Charsets.UTF_8).forEach { result += it }
        } catch (e: IOException) {
            throw IOException("Unable to write to file")
        } catch (e: SecurityException) {
            throw SecurityException("Do not have enough rights to read this file")
        }

        return result
    }
}