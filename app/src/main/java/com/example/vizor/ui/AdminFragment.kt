package com.example.vizor.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.vizor.R
import com.g00fy2.quickie.QRResult
import com.g00fy2.quickie.ScanQRCode
import java.util.*

class AdminFragment : Fragment() {

    private val scanQrCode = registerForActivityResult(ScanQRCode()) { handleResult(it) }

    private fun handleResult(it: QRResult?) {
        when (it) {
            is QRResult.QRSuccess -> {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_admin2, container, false)

        requireView().findViewById<EditText>(R.id.adminDiseaseSearch).visibility = View.INVISIBLE

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scanQrCode.launch(null)
    }
}