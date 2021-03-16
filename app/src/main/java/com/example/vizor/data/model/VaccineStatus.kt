package com.example.vizor.data.model

import java.util.*

data class VaccineStatus(val confirmationDate: String?, val firstDateReceived: String?, val secondDoseReceived: String?, val fullResistanceAchieved: String?) {
    companion object {
        public fun getFromHashMap(hashMap: HashMap<String, Any?>): VaccineStatus {
            return VaccineStatus(
                    hashMap["confirmationDate"] as String, hashMap["firstDateReceived"] as String, hashMap["secondDoseReceived"] as String, hashMap["fullResistanceAchieved"] as String
            )
        }
    }

    public fun generateHashMap(): HashMap<String, Any?> {
        return hashMapOf(
                "confirmationDate" to confirmationDate,
                "firstDateReceived" to firstDateReceived,
                "secondDoseReceived" to secondDoseReceived,
                "fullResistanceAchieved" to fullResistanceAchieved,
        )
    }
}