package com.example.vizor.data.model

import com.example.vizor.data.model.MainViewModel.Companion.currentUser
import com.example.vizor.ui.LoginFragment
import com.example.vizor.ui.RegistrationFragment
import com.example.vizor.ui.VaccineFragment
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

data class User(var password: String, val ID: String) {

    private var documentRef = Firebase.firestore.collection("users").document(ID)
    var myVaccines: ArrayList<Vaccine> = ArrayList()

    init {
        myVaccines.add(Vaccine("COVID-19", generateRandomVaccineStatus(), generateRandomVaccineName("COVID-19")))
        myVaccines.add(Vaccine("Hepatitis B", generateRandomVaccineStatus(), generateRandomVaccineName("Hepatitis B")))
        myVaccines.add(Vaccine("Polio", generateRandomVaccineStatus(), generateRandomVaccineName("Polio")))
        myVaccines.add(Vaccine("Diphtheria", generateRandomVaccineStatus(), generateRandomVaccineName("Diphtheria")))
        myVaccines.add(Vaccine("Tetanus", generateRandomVaccineStatus(), generateRandomVaccineName("Tetanus")))
        myVaccines.add(Vaccine("H1N1", generateRandomVaccineStatus(), generateRandomVaccineName("H1N1")))
        myVaccines.add(Vaccine("Typhoid", generateRandomVaccineStatus(), generateRandomVaccineName("Typhoid")))
        myVaccines.add(Vaccine("Yellow Fever", generateRandomVaccineStatus(), generateRandomVaccineName("Yellow Fever")))
    }

    private fun generateRandomVaccineStatus(): VaccineStatus {
        return when (Random().nextInt(12)+1) {
            1 -> {
                VaccineStatus("12/03/21","12/04/21","21/04/21","03/05/21")
            }
            2 -> {
                VaccineStatus("8/03/21", "19/03/21","26/03/21","31/03/21")
            }
            3 -> {
                VaccineStatus("5/01/21","12/01/21","19/01/21", "2/02/21")
            }
            4 -> {
                VaccineStatus("27/02/21","5/03/21","12/03/21", "21/03/21")
            }
            5 -> {
                VaccineStatus("20/02/21","27/02/21","5/03/21", "12/03/21")
            }
            6 -> {
                VaccineStatus("13/02/21","20/03/21","27/03/21", "05/04/21")
            }
            7 -> {
                VaccineStatus("7/03/21","10/03/21","14/03/21", "16/04/21")
            }
            8 -> {
                VaccineStatus("2/02/21","15/02/21","22/02/21", "05/03/21")
            }
            9 -> {
                VaccineStatus("15/03/21","2/04/21","17/04/21", "30/04/21")
            }
            else -> {
                VaccineStatus("", "", "", "")
            }
        }
    }

    private fun generateRandomVaccineName(vaccineName: String): String {
        val r = Random()
        return when (vaccineName) {
            "COVID-19" -> {
                return when (r.nextInt(3)) {
                    0 -> "Pfizer-BioNTech"
                    1 -> "Moderna"
                    2 -> "Johnson & Johnson / Janssen"
                    else -> ""
                }
            }
            "Hepatitis B" -> {
                return when (r.nextInt(3)) {
                    0 -> "Recombivax HB"
                    1 -> "Engerix-B"
                    2 -> "Heplisav-B"
                    else -> ""
                }
            }
            "Polio" -> {
                return when (r.nextInt(3)) {
                    0 -> "Ipol"
                    1 -> "Poliovax"
                    2 -> "Salk"
                    else -> ""
                }
            }
            "Diphtheria" -> {
                return when (r.nextInt(3)) {
                    0 -> "Pentacel"
                    1 -> "Trihibit"
                    2 -> "Kinrix"
                    else -> ""
                }
            }
            "Tetanus" ->  {
                return when (r.nextInt(3)) {
                    0 -> "Tenivac"
                    1 -> "Decavac"
                    2 -> "Tetramune"
                    else -> ""
                }
            }
            "H1N1" -> {
                return when (r.nextInt(3)) {
                    0 -> "Afluria"
                    1 -> "Fluarix"
                    2 -> "Fluzone"
                    else -> ""
                }
            }
            "Typhoid" -> {
                return when (r.nextInt(3)) {
                    0 -> "Typhim Vi"
                    1 -> "Vivotif"
                    2 -> "Typhim Vi Polysaccharide"
                    else -> ""
                }
            }
            "Yellow Fever" -> {
                return when (r.nextInt(2)) {
                    0 -> "YF-Vax"
                    1 -> "Stamaril"
                    else -> ""
                }
            }
            else -> ""
        }
    }

    companion object {
        private fun getFromCloud(ID: String, isFromLoginFragment: Boolean) {
            val documentRef = Firebase.firestore.document("users/${ID}")

            documentRef.get().addOnSuccessListener { result ->
                val vaccineHashMap = result.get("myVaccines") as ArrayList<HashMap<String, Any?>>
                val vaccines: ArrayList<Vaccine> = vaccineHashMap.map { hashMap -> Vaccine.getFromHashMap(hashMap) } as ArrayList<Vaccine>
                currentUser = User(result.getString("password")!!, result.getString("ID")!!, vaccines)

                if (isFromLoginFragment) {
                    LoginFragment.loginFragment?.onUserUpdated()
                }
                val vaccinationStatus = currentUser!!.generateVaccinesStatus()
                VaccineFragment.vaccineStatus = vaccinationStatus
                if (VaccineFragment.adapter != null) {
                    VaccineFragment.adapter!!.setStatuses(vaccinationStatus)
                }
            }
        }

        fun tryLogin(ID: String, password: String, isFromLoginFragment: Boolean) {
            val referenceToUsers = Firebase.firestore.collection("users").get()
            referenceToUsers.addOnSuccessListener { result ->
                for (document in result.documents) {
                    if (((document.get("ID")!! as String) == ID && (document.get("password")!! as String) == password)) {
                        getFromCloud(document.id, isFromLoginFragment)
                    }
                }
            }
        }

        fun registerUser(ID: String, password: String) {
            val referenceToUsers = Firebase.firestore.collection("users").get()
            referenceToUsers.addOnSuccessListener { result ->
                var isValid = ID.matches(Regex("[ST][0-9]{7}[A-Z]")) && password.length >= 8
                for (document in result.documents) {
                    if (((document.get("ID")!! as String) == ID)) {
                        isValid = false
                    }
                }

                if (isValid) {
                    val user = User(password, ID)
                    user.commitToCloud()
                    currentUser = user
                }
                RegistrationFragment.registrationFragment?.onUserUpdated(isValid)
            }
        }
    }

    constructor(password: String, ID: String, myVaccines: ArrayList<Vaccine>) : this(password, ID) {
        this.myVaccines = myVaccines
    }

    fun commitToCloud() {
        documentRef.set(getHashMap())
    }

    fun updateData() {
        documentRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                documentRef.set(getHashMap(), SetOptions.merge())
            }
        }
    }

    private fun getHashMap(): HashMap<String, Any?> {
        val user: HashMap<String, Any?> = hashMapOf(
            "password" to password, "ID" to ID, "myVaccines" to myVaccines
        )

        return user
    }

    fun deleteAccount() {
        documentRef.get().addOnSuccessListener { documentRef ->
            documentRef.reference.delete()
        }
        MainViewModel.currentUser = null
    }

    fun generateVaccinesStatus(): Array<String> {
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