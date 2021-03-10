package com.example.vizor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.vizor.R

class RegistrationFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController
    lateinit var passwordEditText: EditText
    lateinit var userEditText: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var a = inflater.inflate(R.layout.fragment_registration, container, false)
        return a
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        requireView().findViewById<Button>(R.id.regisBtn).setOnClickListener(this)

        passwordEditText = view.findViewById(R.id.editTextTextPasswordRegistration)
        userEditText = view.findViewById(R.id.editTextTextPersonNameRegistration)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.regisBtn -> {
                val password = passwordEditText.text.toString(); val ID = userEditText.text.toString()
                if (!ID.matches(Regex("[ST][0-9]{5}[A-Z]"))) {
                    Toast.makeText(context, "Please enter a valid ID: Follows standard Singapore ID structure ", Toast.LENGTH_SHORT)
                } else if (password.length < 8) {
                    Toast.makeText(context, "Please enter a valid password: At least 8 characters", Toast.LENGTH_SHORT)
                }

                navController.navigate(R.id.action_registrationFragment_to_threeFragment)
            }
        }
    }
}