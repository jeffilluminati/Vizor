package com.example.eventtracker.data.model

import android.graphics.Bitmap
import com.example.vizor.data.model.AES
import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.image.ImageType
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.SecretKeySpec

class QR(plainText: String, secret: String) {
    private var qrCode: QRCode =
        QRCode.from(AES.encrypt(plainText.toByteArray(), SecretKeySpec(SECRET.toByteArray(), "AES")).decodeToString())

    companion object {
        const val SECRET = "SECRET"
    }

    public fun getQRCodeBMP(): QRCode? {
        return qrCode.to(ImageType.BMP)
    }
}