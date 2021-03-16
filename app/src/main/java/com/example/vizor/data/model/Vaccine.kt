package com.example.vizor.data.model

data class Vaccine(var diseaseName: String, var vaccineStatus: VaccineStatus, var vaccineName: String) {
    companion object {
        public fun getFromHashMap(hashMap: HashMap<String, Any?>): Vaccine {
            return Vaccine(
                    hashMap["diseaseName"] as String,
                    VaccineStatus.getFromHashMap(hashMap["vaccineStatus"] as HashMap<String, Any?>),
                    hashMap["vaccineName"] as String
            )
        }
    }

    public fun generateHashMap(): HashMap<String, Any?> {
        return hashMapOf("diseaseName" to diseaseName, "vaccineStatus" to vaccineStatus, "vaccineName" to vaccineName)
    }
}