package com.example.vizor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.vizor.MainActivity
import com.example.vizor.R
import com.example.vizor.data.model.MainViewModel
import com.example.vizor.data.model.MainViewModel.Companion.currentUser
import com.example.vizor.data.model.User

class RegistrationFragment : Fragment(), View.OnClickListener {
    lateinit var navController: NavController
    lateinit var passwordEditText: EditText
    lateinit var userEditText: EditText
    lateinit var warningTextView: TextView


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
        warningTextView = view.findViewById(R.id.warningTextRegistration)
        warningTextView.text = ""
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.regisBtn -> {
                val user = User.registerUser(userEditText.text.toString(), userEditText.text.toString())
                if (user != null) {
                    MainViewModel.currentUser = user
                    navController.navigate(R.id.action_registrationFragment_to_threeFragment)
                } else {
                    warningTextView.text = "Your ID must follow Singapore ID convention and password must have a minimum of 8 characters."
                }
            }
        }
    }
}