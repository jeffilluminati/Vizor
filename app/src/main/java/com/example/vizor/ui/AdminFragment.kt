package com.example.vizor.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vizor.R
import com.example.vizor.data.model.CryptUtil
import com.example.vizor.data.model.MainViewModel
import com.example.vizor.data.model.User
import com.example.vizor.data.model.Vaccine
import com.g00fy2.quickie.QRResult
import com.g00fy2.quickie.ScanQRCode
import java.text.SimpleDateFormat
import java.util.*

class AdminFragment : Fragment() {

    companion object {
        var vaccines: ArrayList<Vaccine>? = null
        var adapter: AdminDiseaseRecyclerViewAdapter? = null
        var adminFragment: AdminFragment? = null
    }

    private val scanQrCode = registerForActivityResult(ScanQRCode()) { handleResult(it) }
    private lateinit var root: View
    private lateinit var scannedUser: User

    private fun handleResult(it: QRResult?) {

        when (it) {
            is QRResult.QRSuccess -> {
                val ID = CryptUtil.decrypt(it.content.rawValue)
                User.peekDetails(ID)

            }
            is QRResult.QRError -> {
                AlertDialog.Builder(root.context)
                        .setTitle("Error!")
                        .setMessage("There was an error reading the QR, it was not valid.")
                        .setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                            MainViewModel.navController!!.navigate(R.id.action_adminFragment_to_enterFragment)
                        }
            }
            is QRResult.QRUserCanceled -> {
                MainViewModel.navController!!.navigate(R.id.action_adminFragment_to_enterFragment)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_admin, container, false)
        adminFragment = this

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        root.visibility = View.INVISIBLE
        scanQrCode.launch(null)

        var recyclerView = this.requireView().findViewById<RecyclerView>(R.id.adminDiseaseRecyclerView)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView?.adapter = AdminDiseaseRecyclerViewAdapter()
        adapter = recyclerView?.adapter as AdminDiseaseRecyclerViewAdapter

        val searchTo = requireView().findViewById<EditText>(R.id.adminDiseaseSearch)

        searchTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var diseaseList = (recyclerView!!.adapter as AdminDiseaseRecyclerViewAdapter).getDiseaseList()
                for (a in diseaseList.indices){
                    Log.i(diseaseList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())
                    if (diseaseList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }


                (recyclerView!!.adapter as AdminDiseaseRecyclerViewAdapter).updateArrays(posList.toTypedArray())
                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.i(s.toString(), "A")
                // you can call or do what you want with your EditText here

                var posList = listOf<Int>()
                var diseaseList = (recyclerView!!.adapter as AdminDiseaseRecyclerViewAdapter).getDiseaseList()
                for (a in diseaseList.indices){
                    Log.i(diseaseList[a].toLowerCase(), requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())
                    if (diseaseList[a].toLowerCase().contains(requireView().findViewById<EditText>(R.id.adminDiseaseSearch).text.toString().toLowerCase())){
                        posList += a
                    }
                }


                (recyclerView!!.adapter as AdminDiseaseRecyclerViewAdapter).updateArrays(posList.toTypedArray())
            }
        })

        val vaccinationStatus = arrayOf("Vaccine Pending", "Vaccine Received", "No Vaccine", "Vaccine Pending", "Vaccine Received", "Vaccine Pending", "Vaccine Received", "No Vaccine")
        (recyclerView!!.adapter as AdminDiseaseRecyclerViewAdapter).setStatuses(vaccinationStatus)
    }

    fun updateDetails() {
        adapter!!.setStatuses(generateVaccinesStatus(vaccines!!))
        root.visibility = View.VISIBLE
    }

    fun generateVaccinesStatus(myVaccines: ArrayList<Vaccine>): Array<String> {
        val strArray: Array<String> = Array(8) { "" }
        for (i in 0..7) {
            if (myVaccines[i].vaccineStatus.fullResistanceAchieved.equals("")) {
                strArray[i] = "No Vaccine"
            } else if (Date().after(SimpleDateFormat("dd/MM/yy").parse(myVaccines[i].vaccineStatus.fullResistanceAchieved))) {
                strArray[i] = "Vaccine Received"
            } else {
                strArray[i] = "Vaccine Pending"
            }
        }

        return strArray
    }
}