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

    override suspend fun addAppointment(appointment: Appointment) {
        db.appointmentDao().addAppointment(appointment)
    }

    override suspend fun deleteAppointment(appointment: Appointment) {
        db.appointmentDao().deleteAppointment(appointment)
    }

    override fun getAppointments(): Flow<List<Appointment>> = db.appointmentDao().getAllAppointments()
}