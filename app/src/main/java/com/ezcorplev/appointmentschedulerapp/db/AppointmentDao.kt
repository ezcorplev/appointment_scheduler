package com.ezcorplev.appointmentschedulerapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert
    fun addAppointment(appointment: Appointment): Long // check if needs suspend

    @Delete
    fun deleteAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments")
            // ORDER BY name COLLATE NOCASE ASC")

    fun getAppointments(): Flow<List<Appointment>>
}