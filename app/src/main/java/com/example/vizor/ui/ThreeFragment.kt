package com.example.vizor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vizor.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ThreeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var a = inflater.inflate(R.layout.fragment_three, container, false)
        val navView: BottomNavigationView = requireView().findViewById(R.id.nav_view)

        val navController = findNavController(this.requireActivity(), R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_travelID, R.id.navigation_vaccines, R.id.navigation_history))
        navView.setupWithNavController(navController)
        return a
    }

}