package com.example.vizor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vizor.R

class VaccineFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val a = inflater.inflate(R.layout.fragment_vaccines, container, false)
        return a
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = this.requireView().findViewById<RecyclerView>(R.id.diseaseRecylerView)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView?.adapter = DiseaseRecyclerViewAdapter()



    }
}