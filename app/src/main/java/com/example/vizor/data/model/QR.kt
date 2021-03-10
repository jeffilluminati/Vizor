package com.example.eventtracker.data.model

import android.graphics.Bitmap
import com.example.vizor.data.model.AES
import net.glxn.qrgen.android.QRCode

class QR(plainText: String, secret: String, ) {
//    private var qrCode: QRCode

    companion object {
        public fun generateQRCode(text: String): Bitmap {
            return QRCode.from(text).withSize(1000, 1000).bitmap()
        }
    }

    init {
//        qrCode = QRCode.from(AES.encrypt(plainText.toByteArray()))
    }
}