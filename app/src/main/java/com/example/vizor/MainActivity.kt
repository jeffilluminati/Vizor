package com.example.vizor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.Navigation
import com.example.vizor.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var navControl = Navigation.findNavController(this, R.id.navHostFragment)
        supportActionBar!!.hide()

        if (auth.currentUser != null) {
            navControl.navigate(R.id.action_loginFragment_to_threeFragment)
        }
    }
}