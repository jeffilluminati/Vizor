package com.example.vizor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.vizor.R
import com.example.vizor.data.model.MainViewModel
import com.example.vizor.data.model.QR

class IdFragment : Fragment(), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var imageView: ImageView
    private lateinit var qr: QR

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val a = inflater.inflate(R.layout.fragment_id, container, false)
        return a
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        requireView().findViewById<ImageButton>(R.id.settingsBtn).setOnClickListener(this)
        imageView = requireView().findViewById(R.id.QRCodeView)

        if (MainViewModel.currentUser != null) {
            requireView().findViewById<TextView>(R.id.travelIdTextView).text = "Your Travel ID: ${MainViewModel.currentUser!!.ID}"
            qr = QR(MainViewModel.currentUser!!.ID)
            imageView.setImageBitmap(qr.getBMP())
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.settingsBtn -> navController.navigate(R.id.action_navigation_id_to_profileFragment)
        }
    }
}