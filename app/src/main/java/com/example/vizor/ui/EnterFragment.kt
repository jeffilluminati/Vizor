package com.example.vizor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.vizor.R

class EnterFragment: Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var a = inflater.inflate(R.layout.fragment_enter, container, false)

        return a
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        requireView().findViewById<Button>(R.id.registerBtn).setOnClickListener(this)
        requireView().findViewById<Button>(R.id.infoBtn).setOnClickListener(this)
        requireView().findViewById<Button>(R.id.loginBtn).setOnClickListener(this)
    }

    override fun onClick(v: View?){
        when(v!!.id){
            R.id.loginBtn -> navController.navigate(R.id.action_enterFragment_to_loginFragment)
            R.id.registerBtn -> navController.navigate(R.id.action_enterFragment_to_registrationFragment)
            R.id.infoBtn -> navController.navigate(R.id.action_enterFragment_to_infoFragment)
        }
    }
}