package com.ezcorplev.appointmentschedulerapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezcorplev.appointmentschedulerapp.enums.State
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

    val stateLiveData = MutableLiveData(State.IDLE)

    fun addOrEditAppointment(appointment: Appointment) {
        viewModelScope.launch(Dispatchers.IO) {
            stateLiveData.postValue(State.LOADING)
            appointmentRepo.addAppointment(appointment)
            stateLiveData.postValue(State.ADDED)
        }
    }
}