package com.example.vizor.data.model

import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class User(var uid: String, val schoolId: String) {

    private var documentRef = uid.let { Firebase.firestore.collection("users").document(it) }

    constructor(uid: String, schoolId: String, myVaccines: ArrayList<Vaccine>) : this(uid, schoolId) {
        this.documentRef = Firebase.firestore.collection("users").document(uid)
    }

    companion object {
        public fun getFromCloud(uid: String) : User {
            val documentRef = Firebase.firestore.document("users/${uid}")
            return User(uid, documentRef.get().result?.get("schoolId") as String, documentRef.get().result?.get("myVaccines") as ArrayList<Vaccine>)
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
            "schoolId" to schoolId,
        )

        return user
    }
}