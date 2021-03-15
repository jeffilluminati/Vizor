package com.example.vizor.data.model

import com.example.vizor.data.model.MainViewModel.Companion.currentUser
import com.example.vizor.ui.LoginFragment
import com.example.vizor.ui.RegistrationFragment
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class User(var password: String, val ID: String, val myVaccines: ArrayList<Vaccine> = ArrayList()) {

    private var documentRef = Firebase.firestore.collection("users").document(ID)

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