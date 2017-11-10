/**
 * @author Alec Burgdorf
 * @version 0.1.0
 */
@file:JvmName("Checksum")

package com.github.aburgd.chksm.checksum

import com.github.aburgd.chksm.digesttools.DigestTools
import com.github.aburgd.chksm.fileops.FileOps
import java.util.*

/**
 * Contains the main method/function for user interaction and user interfaces
 */
object Checksum {
    @JvmStatic
    fun main(args: Array<String>) {
        // make an array to store the created digests
        val digests: MutableList<ByteArray> = mutableListOf<ByteArray>()

        if (args.isNotEmpty()) {
            // the algorithm is always the second argument to the main method
            val digestAlgorithm: String = args[0]

            // and everything after are filenames
            val filenames: Array<String> = Arrays.copyOfRange(args, 1, args.size)

            // so we iterate over those filenames and open them up using FileOps.openFile
            for (filename: String in filenames) {
                // and digest them...
                val openFile: ByteArray = FileOps.openFile(filename)
                var newDigest: ByteArray = kotlin.ByteArray(0)

                when (digestAlgorithm) {
                    "md5" -> newDigest = DigestTools.getDigest(openFile, "MD5")
                    "sha1" -> newDigest = DigestTools.getDigest(openFile, "SHA-1")
                    "sha256" -> newDigest = DigestTools.getDigest(openFile, "SHA-256")
                    "sha384" -> newDigest = DigestTools.getDigest(openFile, "SHA-384")
                    "sha512" -> newDigest = DigestTools.getDigest(openFile, "SHA-512")
                }
                // ... and add them to the MutableList...
                digests.add(newDigest)
            }
            // iterate over filenames so we can print them with their proper digests
            for (filename: String in filenames) {
                // and use them to get their respective digests from the list
                val currentDigest: String = DigestTools.digestFormatter(digests[filenames.indexOf(filename)])
                print("$filename:\t$currentDigest")
                println()
            }
        } else {
            // all else fails, give 'em hel~~l~~p
            printHelp()
        }
    }

    private fun printHelp() {
        val help: String = "usage: chksm [algorithm] [file]\n" +
                "algorithms available: md5, sha1, sha256,\nsha384, sha512\n\n"
        print(help)
    }
}
