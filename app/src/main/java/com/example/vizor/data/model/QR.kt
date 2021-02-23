package com.example.eventtracker.data.model

import android.graphics.Bitmap
import net.glxn.qrgen.android.QRCode

class QR {
    companion object {
        public fun generateQRCode(text: String): Bitmap {
            return QRCode.from(text).withSize(1000, 1000).bitmap()
        }
    }
}