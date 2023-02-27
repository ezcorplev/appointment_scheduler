package com.ezcorplev.appointmentschedulerapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezcorplev.appointmentschedulerapp.enums.AppointmentLocation
import java.io.Serializable
import java.sql.Time
import java.util.*

@Entity(tableName = "appointments")
class Appointment(
    @PrimaryKey
    val id: Long,
    val date: Date,
    val time: Date,
    val location: AppointmentLocation,
    val description: String
): Serializable