package com.ezcorplev.appointmentschedulerapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.repos.AppointmentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddOrEditAppointmentViewModel @Inject constructor(
    private val appointmentRepo: AppointmentRepo
): ViewModel() {

    fun addOrEditAppointment(id: Long, date: String, time: String, desc: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val appointment = Appointment(id, date, time, desc)

            appointmentRepo.addAppointment(appointment)
        }
    }
}