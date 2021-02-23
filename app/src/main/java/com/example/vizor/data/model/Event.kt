package com.example.eventtracker.data.model

import com.example.eventtracker.MainActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


data class Event(var name: String, var eventDescription: String, var ownerUid: String, var attendees: ArrayList<String>, var eventId: String) {
    var isExposed: Boolean = false
    private val documentRef = eventId.let { Firebase.firestore.collection("events").document(it) }

    constructor(name: String, eventDescription: String) : this(name, eventDescription, MainActivity.user.uid.toString(), ArrayList(), generateEventId(MainActivity.user.uid.toString())) {
        attendees.add(ownerUid)
        documentRef.set(getHashMap())
    }

    companion object {
        public fun getEventFromCloud(eventId: String) : Event{
            val documentRef = Firebase.firestore.document("events/${eventId}")

            return generateEventFromHashMap(documentRef.get().result?.data!!)
        }

        public fun generateEventFromHashMap(hashMap: Map<String, Any?>): Event {
            return Event(
                    hashMap["name"] as String, hashMap["eventDescription"] as String, hashMap["ownerUid"] as String,
                        hashMap["attendees"] as ArrayList<String>, hashMap["eventid"] as String)
        }

        private fun generateEventId(ownerUid: String): String {
            val rootRef: FirebaseFirestore = FirebaseFirestore.getInstance()
            var doesExist = true
            var tempEventId = ""

            while (doesExist) {
                tempEventId = ownerUid + Random().nextLong()
                val docIdRef: DocumentReference = rootRef.document("events/${tempEventId}")

                docIdRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        doesExist = task.result!!.exists()
                    }
                }
            }

            return tempEventId
        }
    }


    public fun addAttendee(attendeeId: String) {
        attendees.add(attendeeId)

        documentRef.get().addOnSuccessListener { documentSnapshot -> run {
            if (documentSnapshot.exists()) {
                val event = getHashMap()
                documentRef.set(event, SetOptions.merge())
            }
        } }
    }

    private fun getHashMap(): HashMap<String, Any?> {
        val event: HashMap<String, Any?>  = hashMapOf(
            "eventId" to eventId,
            "name" to name,
            "ownerUid" to ownerUid,
            "attendees" to attendees,
            "isExposed" to isExposed,
            "eventDescription" to eventDescription,
        )

        return event
    }

}