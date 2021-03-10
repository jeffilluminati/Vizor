package com.example.vizor

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import com.example.vizor.data.model.MainViewModel
import com.example.vizor.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var navControl = Navigation.findNavController(this, R.id.navHostFragment)
        supportActionBar!!.hide()

        if (auth.currentUser != null) {
            MainViewModel.currentUser = User.getFromCloud(auth.currentUser!!.uid)
            navControl.navigate(R.id.action_loginFragment_to_threeFragment)
        } else {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("MainActivity", "signInAnonymously:success")
                        val user = auth.currentUser
                    } else {
                        Log.w("MainActivity", "signInAnonymously:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}