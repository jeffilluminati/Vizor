package com.example.eventtracker.data.model

import android.graphics.Bitmap
import com.example.vizor.data.model.AES
import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.image.ImageType
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.SecretKeySpec

class QR(plainText: String, secret: String, ) {
    private var qrCode: QRCode

    companion object {
        public fun generateQRCode(text: String): Bitmap {
            return QRCode.from(text).withSize(1000, 1000).bitmap()
        }
    }

    init {
        val secret = "Secret"
        qrCode = QRCode.from(AES.encrypt(plainText.encodeToByteArray(), SecretKeySpec(secret.encodeToByteArray(), "AES")).decodeToString())
    }

    public fun getQRCodeBMP(): Bitmap {
        return qrCode.bitmap()
    }
}