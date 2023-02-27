package com.ezcorplev.appointmentschedulerapp.enums

enum class AppointmentLocation(val value: String, val locationRes: Int) {
    DALLAS("Dallas", 0),
    MEMPHIS("Memphis", 1),
    ORLANDO("Orlando", 2),
    PARKCITY("Park City", 3),
    SANFRANSISCO("San Fransisco", 4),
    STGEORGE("St. George", 5);


    override fun toString(): String {
        return this.value
    }

    companion object {
        private val map = values().associateBy(AppointmentLocation::value)
        fun fromString(type: String) = map[type]
    }
}
