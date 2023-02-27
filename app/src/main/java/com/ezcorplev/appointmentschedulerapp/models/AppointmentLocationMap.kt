package com.ezcorplev.appointmentschedulerapp.models

import com.ezcorplev.appointmentschedulerapp.enums.AppointmentLocation

data class AppointmentLocationMap(
    val locationMap: Map<AppointmentLocation, String>
)
