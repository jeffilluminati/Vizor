package com.example.vizor.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.vizor.R
import com.example.vizor.data.model.MainViewModel
import com.example.vizor.data.model.Vaccine
import com.example.vizor.data.model.VaccineStatus
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.text.SimpleDateFormat
import java.util.*

class DiseaseFragment: Fragment(), View.OnClickListener {
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val a = inflater.inflate(R.layout.fragment_disease, container, false)
        return a
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
//        requireView().findViewById<ImageButton>(R.id.settingsBtn).setOnClickListener(this)

        val disease: String = requireArguments().get("disease").toString()
        val status: String = requireArguments().get("status").toString()

        requireView().findViewById<TextView>(R.id.diseaseFragmentName).text = disease
        requireView().findViewById<TextView>(R.id.diseaseFragmentStatus).text = status

        val vaccine: Vaccine = MainViewModel.currentUser!!.myVaccines.find{ vaccine: Vaccine -> vaccine.diseaseName.equals(disease, true)}!!
        val vaccineStatus: VaccineStatus = vaccine.vaccineStatus
        val sdf = SimpleDateFormat("dd/MM/yy")
        var dateList: Array<String> = arrayOf(vaccineStatus.confirmationDate!!, vaccineStatus.firstDateReceived!!, vaccineStatus.secondDoseReceived!!, vaccineStatus.fullResistanceAchieved!!)

        when (status){
            "No Vaccine" -> {
                requireView().findViewById<RelativeLayout>(R.id.vaccinationProgress).visibility = View.GONE
                requireView().findViewById<LinearLayout>(R.id.nameBackground).setBackgroundColor(requireContext().resources.getColor(R.color.no_vaccine))
                requireView().findViewById<TextView>(R.id.infoTextView).text = "No vaccination has been received for the disease ${disease}."
            }
            "Vaccine Pending" ->{
                requireView().findViewById<LinearLayout>(R.id.nameBackground).setBackgroundColor(requireContext().resources.getColor(R.color.vaccine_pending))
                requireView().findViewById<TextView>(R.id.infoTextView).text = "Vaccine to be taken: ${vaccine.vaccineName}"
                var sampleDateIntList = arrayOf(dateToInt(dateList[0]), dateToInt(dateList[1]), dateToInt(dateList[2]), dateToInt(dateList[3]))
                var intTodayDate = dateToInt(sdf.format(Date()))

                requireView().findViewById<TextView>(R.id.vaccinationConfirmDate).text = dateList[0] + " Vaccination Confirmed"
                requireView().findViewById<TextView>(R.id.vaccinationFirstDoseDate).text = dateList[1] + " Receiving of First Dose"
                requireView().findViewById<TextView>(R.id.vaccinationSecondDoseDate).text = dateList[2] + " Receiving of Second Dose"
                requireView().findViewById<TextView>(R.id.vaccinationResistanceDate).text = "~" + dateList[3] + " Resistance Achieved"

                if (intTodayDate >= sampleDateIntList[2]){
                    requireView().findViewById<LinearProgressIndicator>(R.id.progressBar).progress = 34
                }
                else if (intTodayDate >= sampleDateIntList[1])
                    requireView().findViewById<LinearProgressIndicator>(R.id.progressBar).progress = 21
                else if (intTodayDate >= sampleDateIntList[0])
                    requireView().findViewById<LinearProgressIndicator>(R.id.progressBar).progress = 8
                else
                    requireView().findViewById<LinearProgressIndicator>(R.id.progressBar).progress = 4

            }
            "Vaccine Received" ->{
                requireView().findViewById<LinearProgressIndicator>(R.id.progressBar).progress = 58
                requireView().findViewById<TextView>(R.id.vaccinationConfirmDate).text = dateList[0] + " Vaccination Confirmed"
                requireView().findViewById<TextView>(R.id.vaccinationFirstDoseDate).text = dateList[1] + " Receiving of First Dose"
                requireView().findViewById<TextView>(R.id.vaccinationSecondDoseDate).text = dateList[2] + " Receiving of Second Dose"
                requireView().findViewById<TextView>(R.id.vaccinationResistanceDate).text =  "~" +  dateList[3] + " Resistance Achieved"
                requireView().findViewById<LinearLayout>(R.id.nameBackground).setBackgroundColor(requireContext().resources.getColor(R.color.vaccine_received))
                requireView().findViewById<TextView>(R.id.infoTextView).text = "Vaccine taken: ${vaccine.vaccineName}"
            }
        }

        when (disease){
            "COVID-19" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_covid)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/COVID-19"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
            "Hepatitis B" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_hepatitis)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/Hepatitis_B_virus"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
            "Polio" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_polio)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/Polio"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
            "Diphtheria" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_diphtheria)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/Diphtheria"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
            "Tetanus" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_tetanus)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/Tetanus"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
            "H1N1" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_h1n1)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/Influenza_A_virus_subtype_H1N1"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
            "Typhoid" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_typhoid)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/Typhoid_fever"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
            "Yellow Fever" ->{
                requireView().findViewById<ImageView>(R.id.diseaseFragmentImageView).setImageResource(R.drawable.disease_yellowfever)
                requireView().findViewById<Button>(R.id.moreInfoButton).setOnClickListener{
                    val url = "https://en.wikipedia.org/wiki/Yellow_fever"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
            }
        }

        val sdf1 = SimpleDateFormat("dd MMMM yyyy")
        requireView().findViewById<TextView>(R.id.verifiedText).text = "Information last verified by MOH Vizor on " + sdf1.format(Date()) + "."


    }

    override fun onClick(v: View?) {
//        when (v!!.id) {
//            R.id.settingsBtn -> navController.navigate(R.id.action_navigation_id_to_profileFragment)
//        }
    }




    fun dateToInt(a:String): Int{
        var sum = 0
        var s = a.split("/")
        sum = s[0].toInt() + s[1].toInt() * 35 + s[2].toInt() * 500
        return sum
    }
}