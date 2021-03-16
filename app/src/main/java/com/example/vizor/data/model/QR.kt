package com.example.vizor.data.model

import android.graphics.Bitmap
import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.image.ImageType

class QR(plainText: String) {
    private var qrCode: QRCode =
        QRCode.from(CryptUtil.encrypt(plainText))


    fun getQRCodeBMP(): QRCode? {
        return qrCode.to(ImageType.BMP)
    }

    fun getBMP(): Bitmap {
        return qrCode.bitmap()
    }
}