package com.ezcorplev.appointmentschedulerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.repos.AppointmentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appointmentRepo: AppointmentRepo
): ViewModel() {

    var appointments: Flow<List<Appointment>> = appointmentRepo.getAppointments()
    val onAppointmentDeleted = MutableLiveData<Appointment>()

    init {
        viewModelScope.launch(Dispatchers.IO) {

//            val appointment1 = Appointment(111L, "23/2/23", "16:00", "App Desc")
//            val appointment2 = Appointment(112L, "23/2/23", "16:00", "App Desc2")
//
//            appointmentRepo.addAppointment(appointment1)
//            appointmentRepo.addAppointment(appointment2)
//            appointments = appointmentRepo.getAppointments()
        }
    }

    fun deleteAppointment(appointment: Appointment) {

        viewModelScope.launch(Dispatchers.IO) {
            appointmentRepo.deleteAppointment(appointment)
            onAppointmentDeleted.postValue(appointment)
        }
    }


//    fun getAppointments() {
//        viewModelScope.launch {
//            appointmentRepo.getAppointments()
//        }
//    }
}