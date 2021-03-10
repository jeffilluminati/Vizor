package com.example.vizor.data.model

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES {
    companion object {
        public fun encrypt(plaintext: ByteArray, key: SecretKey, IV: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES")
            val keySpec = SecretKeySpec(key.encoded, "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            val cipherText: ByteArray = cipher.doFinal(plaintext)
            return cipherText
        }

        fun decrypt(cipherText: ByteArray, key: SecretKey): String? {
            try {
                val cipher = Cipher.getInstance("AES")
                val keySpec = SecretKeySpec(key.encoded, "AES")
                cipher.init(Cipher.DECRYPT_MODE, keySpec)
                val decryptedText = cipher.doFinal(cipherText)
                return String(decryptedText)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}