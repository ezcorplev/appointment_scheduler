package com.ezcorplev.appointmentschedulerapp.repos

import android.util.Log
import com.ezcorplev.appointmentschedulerapp.db.AppDatabase
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import kotlinx.coroutines.flow.Flow

interface AppointmentRepo {
    suspend fun addAppointment(appointment: Appointment)
    suspend fun deleteAppointment(appointment: Appointment)
    fun getAppointments(): Flow<List<Appointment>>
}

class AppointmentRepoImpl(
    private val db: AppDatabase
): AppointmentRepo {

    init {

    }

    override suspend fun addAppointment(appointment: Appointment) {
        val a = db.appointmentDao().addAppointment(appointment)
        Log.d("INSERT", "addAppointment: $a") // logging to check appointment added
    }

    override suspend fun deleteAppointment(appointment: Appointment) {
        db.appointmentDao().deleteAppointment(appointment)
    }

    override fun getAppointments(): Flow<List<Appointment>> {
        return db.appointmentDao().getAppointments()
    }
}