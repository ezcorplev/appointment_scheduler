package com.ezcorplev.appointmentschedulerapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "appointments")
class Appointment(
    @PrimaryKey
    val id: Long,
    val date: String,
    val time: String,
    val location: String,
    val description: String
): Serializable