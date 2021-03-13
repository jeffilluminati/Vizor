package com.example.vizor.data.model

import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.image.ImageType
import javax.crypto.spec.SecretKeySpec

class QR(plainText: String, secret: String) {
    private var qrCode: QRCode =
        QRCode.from(AES.encrypt(plainText.toByteArray(), SecretKeySpec(SECRET.toByteArray(), "AES")).decodeToString())

    companion object {
        const val SECRET = "SECRET"
    }

    fun getQRCodeBMP(): QRCode? {
        return qrCode.to(ImageType.BMP)
    }
}