package com.example.vizor.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vizor.R

class HistoryFragment: Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val a = inflater.inflate(R.layout.fragment_history, container, false)
        return a
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = this.requireView().findViewById<RecyclerView>(R.id.countryRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, true)
        recyclerView?.adapter = CountryRecyclerViewAdapter()

    }
}
