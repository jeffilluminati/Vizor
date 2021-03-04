package com.example.vizor.data.model

import java.util.*

enum class VaccineStatus(val confirmationDate: String, val firstDateReceived: String, val secondDoseReceived: String, val fullResistanceAcheived: String) {
    NO_VACCINE("", "", "", ""),
    PENDING("12/02/21", "19/02/21", "25/02/21", "31/04/21"),
    RECEIVED("12/02/21", "19/02/21", "25/02/21", "12/03/21"),
}