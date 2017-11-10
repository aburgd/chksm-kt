package com.github.aburgd.chksm.digesttools

import org.apache.commons.codec.binary.Hex
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * Contains methods for generating and printing digests.
 */
object DigestTools {
    private val stdin: Scanner = Scanner(System.`in`)

    /**
     * Prompts the user for a digest algorithm
     * @param prompt `String` to display to the user
     * @return `String` input for the digest algorithm
     */
//    fun promptForAlgorithm(prompt: String): String {
//        print(prompt)
//        val input: String = stdin.next()
//        return input
//    }

    /**
     * Generates the digest of an object type `Any` with the algorithm [algo] and returns a `ByteArray`
     * @param any `Any` object taken in to digest
     * @param algo `String` for digest algorithm`
     * @return `ByteArray` representation of the digest of [any]
     */
    fun getDigest(any: Any, algo: String?): ByteArray {
        val string: String = any.toString()
        var digest: MessageDigest? = null
        try {
            digest = MessageDigest.getInstance(algo)
        } catch (err: NoSuchAlgorithmException) {
            err.printStackTrace()
        }
        return digest!!.digest(string.toByteArray(StandardCharsets.UTF_8))
    }

    /**
     * Takes in a `ByteArray` of the the digest, encodes it to a hexadecimal `String`,
     * and prints the results formatted by groups of two
     * @param digestArray the `ByteArray` to encode and print
     * @return `String` representation of the [digestArray], formatted
     */
    fun digestFormatter(digestArray: ByteArray): String {
        var digestHex: String = Hex.encodeHexString(digestArray)
        var formatted: String = ""
        // left padding for odd-length strings
        if (digestHex.length % 2 != 0) digestHex = " " + digestHex
        var i: Int = 0
        while (i < digestHex.length) {
            val currentChar: Char = digestHex[i]
            val nextChar: Char = digestHex[i + 1]
            formatted += "$currentChar$nextChar"
            i += 2
        }
        return formatted
    }
}
