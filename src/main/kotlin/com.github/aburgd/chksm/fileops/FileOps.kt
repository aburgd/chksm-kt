package com.github.aburgd.chksm.fileops

import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * Contains methods used for getting files and catching errors
 */
object FileOps {
    private val stdin = Scanner(System.`in`)

    /**
     * Prompts the user for a filename
     * @param prompt `String` to display to the user
     * @return a `ByteArray` representation of the given file
     */
    fun promptForFileIn(prompt: String): ByteArray {
        var done = false
        var newFile: ByteArray
        do {
            print(prompt)
            val filename: String = stdin.next()
            newFile = openFile(filename)
        } while (!done)
        return newFile
    }

    /**
     * Handles errors for [promptForFileIn]
     * @param errorMessage `String` to display for user-friendly error
     * @return void
     */
    fun quitOrTryAgain(errorMessage: String) {
        println(errorMessage)
        println("Do you want to quit (q) or try again (a)? ")
        val answer: Char = stdin.next().toCharArray().get(0)
        if (answer == 'q' || answer == 'Q') {
            System.exit(1)
        }
    }

    /**
     * Performs the actual file opening for [promptForFileIn]
     * @param filename `String` name of file to open
     * @return `ByteArray` representation of file to open
     */
    fun openFile(filename: String): ByteArray {
        var done = false
        var openFile: ByteArray = kotlin.ByteArray(0)
        do {
            try {
                openFile = File(filename).readBytes()
                if (openFile.isNotEmpty()) {
                    done = true
                }
            } catch (err: FileNotFoundException) {
                quitOrTryAgain("ERR - Cannot open file")
            } catch (err: Exception) {
                err.printStackTrace()
            }
        } while (!done)
        return openFile
    }

}
