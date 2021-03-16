package com.example.vizor.data.model

import android.text.format.DateFormat
import com.example.vizor.data.model.MainViewModel.Companion.currentUser
import com.example.vizor.ui.LoginFragment
import com.example.vizor.ui.RegistrationFragment
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

data class User(var password: String, val ID: String) {

    private var documentRef = Firebase.firestore.collection("users").document(ID)
    private var myVaccines: ArrayList<Vaccine> = ArrayList()

    init {
        myVaccines.add(Vaccine("COVID-19", generateRandomVaccineStatus()))
        myVaccines.add(Vaccine("Hepatitis B", generateRandomVaccineStatus()))
        myVaccines.add(Vaccine("Polio", generateRandomVaccineStatus()))
        myVaccines.add(Vaccine("Diphtheria", generateRandomVaccineStatus()))
        myVaccines.add(Vaccine("Tetanus", generateRandomVaccineStatus()))
        myVaccines.add(Vaccine("H1N1", generateRandomVaccineStatus()))
        myVaccines.add(Vaccine("Typhoid", generateRandomVaccineStatus()))
        myVaccines.add(Vaccine("Yellow Fever", generateRandomVaccineStatus()))
    }

    private fun generateRandomVaccineStatus(): VaccineStatus {
        return when (Random().nextInt(7)+1) {
            1 -> {
                VaccineStatus(Date(),SimpleDateFormat("dd/MM/yy").parse("12/04/21"),SimpleDateFormat("dd/MM/yy").parse("19/04/21"),SimpleDateFormat("dd/MM/yy").parse("03/05/21"))
            }
            2 -> {
                VaccineStatus(SimpleDateFormat("dd/MM/yy").parse("12/03/2021"),Date(),SimpleDateFormat("dd/MM/yy").parse("19/04/21"),SimpleDateFormat("dd/MM/yy").parse("03/05/21"))
            }
            3 -> {
                VaccineStatus(SimpleDateFormat("dd/MM/yy").parse("5/03/2021"),SimpleDateFormat("dd/MM/yy").parse("12/03/21"),Date(),SimpleDateFormat("dd/MM/yy").parse("03/05/21"))
            }
            4 -> {
                VaccineStatus(SimpleDateFormat("dd/MM/yy").parse("27/02/2021"),SimpleDateFormat("dd/MM/yy").parse("5/03/21"),SimpleDateFormat("dd/MM/yy").parse("12/03/21"), Date())
            }
            5 -> {
                VaccineStatus(SimpleDateFormat("dd/MM/yy").parse("27/02/2021"),SimpleDateFormat("dd/MM/yy").parse("5/03/21"),SimpleDateFormat("dd/MM/yy").parse("12/03/21"), Date())
            }
            6 -> {
                VaccineStatus(SimpleDateFormat("dd/MM/yy").parse("27/02/2021"),SimpleDateFormat("dd/MM/yy").parse("5/03/21"),SimpleDateFormat("dd/MM/yy").parse("12/03/21"), Date())
            }
            else -> {
                VaccineStatus(SimpleDateFormat("dd/MM/yy").parse("27/02/2021"), SimpleDateFormat("dd/MM/yy").parse("5/03/21"), SimpleDateFormat("dd/MM/yy").parse("12/03/21"), Date())
            }
        }

    }

    companion object {
        private fun getFromCloud(ID: String, isFromLoginFragment: Boolean) {
            val documentRef = Firebase.firestore.document("users/${ID}")

            documentRef.get().addOnSuccessListener { result ->
                currentUser = User(result.getString("password")!!, result.getString("ID")!!, result.get("myVaccines") as ArrayList<Vaccine>)

                if (isFromLoginFragment) {
                    LoginFragment.loginFragment?.onUserUpdated()
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
}