package com.ezcorplev.appointmentschedulerapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@kotlinx.parcelize.Parcelize
@Entity(tableName = "appointments")
class Appointment(
    @PrimaryKey
    val id: Long,
    val date: String,
    val time: String,
    val location: String,
//    val location: AppointmentLocationMap,
    val description: String
): Parcelable {
}