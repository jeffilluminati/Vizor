package com.example.eventtracker.data.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.HashMap
import com.example.eventtracker.data.model.User

data class User(var uid: String, val schoolId: String) {
    public var myEvents: ArrayList<String> = ArrayList()
    public var visitedEvents: ArrayList<String> = ArrayList()

    private var documentRef = uid.let { Firebase.firestore.collection("users").document(it) }

    constructor(uid: String, schoolId: String, visitedEvents: ArrayList<String>, myEvents: ArrayList<String>) : this(uid, schoolId) {
        this.visitedEvents = visitedEvents
        this.myEvents = myEvents
        this.documentRef = Firebase.firestore.collection("users").document(uid)
    }

    companion object {
        public fun getFromCloud(uid: String) : User {
            val documentRef = Firebase.firestore.document("users/${uid}")
            return User(uid, documentRef.get().result?.get("schoolId") as String, documentRef.get().result?.get("visitedEvents") as ArrayList<String>,
                documentRef.get().result?.get("myEvents") as ArrayList<String>)
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

    fun visitEvent(event: Event) {
        visitedEvents.add(event.eventId)

        updateData()
    }

    fun addMyEvent(event: Event) {
        myEvents.add(event.eventId)

        updateData()
    }

    private fun getHashMap(): HashMap<String, Any?> {
        val user: HashMap<String, Any?> = hashMapOf(
            "schoolId" to schoolId,
            "visitedEvents" to visitedEvents,
            "myEvents" to myEvents,
        )

        return user
    }
}