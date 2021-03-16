package com.example.vizor.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vizor.MainActivity
import com.example.vizor.R
import com.example.vizor.data.model.MainViewModel

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

        var recyclerView = this.requireView().findViewById<RecyclerView>(R.id.adminDiseaseRecyclerView)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView?.adapter = DiseaseRecyclerViewAdapter()

        val searchTo = requireView().findViewById<EditText>(R.id.adminDiseaseSearch)

        searchTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var diseaseList = (recyclerView!!.adapter as DiseaseRecyclerViewAdapter).getDiseaseList()
                for (a in diseaseList.indices){
                    Log.i(diseaseList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())
                    if (diseaseList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }


                (recyclerView!!.adapter as DiseaseRecyclerViewAdapter).updateArrays(posList.toTypedArray())
                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.i(s.toString(), "A")
                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var diseaseList = (recyclerView!!.adapter as DiseaseRecyclerViewAdapter).getDiseaseList()
                for (a in diseaseList.indices){
                    Log.i(diseaseList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())
                    if (diseaseList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }


                (recyclerView!!.adapter as DiseaseRecyclerViewAdapter).updateArrays(posList.toTypedArray())
            }
        })

        val vaccinationStatus = MainViewModel.currentUser!!.generateVaccinesStatus()
        (recyclerView!!.adapter as DiseaseRecyclerViewAdapter).setStatuses(vaccinationStatus)
    }
}