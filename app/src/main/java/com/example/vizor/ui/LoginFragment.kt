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
import com.example.vizor.R
import com.example.vizor.data.model.MainViewModel
import com.example.vizor.data.model.User

class LoginFragment: Fragment(), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var warningText: TextView

    companion object {
        var loginFragment: LoginFragment? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val a = inflater.inflate(R.layout.fragment_login, container, false)
        return a
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        requireView().findViewById<Button>(R.id.enterBtn).setOnClickListener(this)

        loginEditText = view.findViewById(R.id.editTextTextPersonNameLogin)
        passwordEditText = view.findViewById(R.id.editTextTextPasswordLogin)
        warningText = view.findViewById(R.id.warningTextLogin)
        warningText.text = ""

        loginFragment = this
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.enterBtn -> User.tryLogin(loginEditText.text.toString(), passwordEditText.text.toString(), true, false)
        }
    }

    fun onUserUpdated() {
        if (MainViewModel.currentUser != null) {
            navController.navigate(R.id.action_loginFragment_to_threeFragment)
        } else {
            warningText.text = "Please enter valid login credentials"
        }
    }
}