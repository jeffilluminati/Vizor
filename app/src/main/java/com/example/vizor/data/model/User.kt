package com.example.vizor.data.model

import android.widget.Toast
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class User(val password: String, val ID: String, val myVaccines: ArrayList<Vaccine> = ArrayList()) {

    private var documentRef = Firebase.firestore.collection("users").document(ID)

    companion object {
        public fun getFromCloud(ID: String) : User {
            val documentRef = Firebase.firestore.document("users/${ID}")

            return User(documentRef.get().result?.get("password") as String,
                documentRef.get().result?.get("ID") as String,
                documentRef.get().result?.get("myVaccines") as ArrayList<Vaccine>)
        }

        public fun tryLogin(ID: String, password: String): User? {
            for (document in
                Firebase.firestore.collection("users")
                .get().result?.documents!!) {
                if (((document.get("ID")!! as String) == ID && (document.get("password")!! as String) == password)) {
                    return getFromCloud(document.id)
                }
            }

            return null
        }

        public fun registerUser(ID: String, password: String): User? {
            return if (!ID.matches(Regex("[ST][0-9]{7}[A-Z]"))) {
                null
            } else if (password.length < 8) {
                null
            } else {
                val user = User(password, ID)
                user.commitToCloud()

                user
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
}