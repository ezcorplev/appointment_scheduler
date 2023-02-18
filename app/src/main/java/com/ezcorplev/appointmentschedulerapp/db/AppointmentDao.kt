package com.ezcorplev.appointmentschedulerapp.db

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAppointment(appointment: Appointment)

    @Delete
    fun deleteAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments ORDER BY date ASC")
    fun getAllAppointments(): Flow<List<Appointment>>
}