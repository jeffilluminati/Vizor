package com.example.vizor.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vizor.R

class ProfileFragment : Fragment(){
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val a = inflater.inflate(R.layout.fragment_profile, container, false)
        return a
    }
}