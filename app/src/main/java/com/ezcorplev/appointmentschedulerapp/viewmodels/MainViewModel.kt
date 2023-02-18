package com.ezcorplev.appointmentschedulerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezcorplev.appointmentschedulerapp.enums.State
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.repos.AppointmentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appointmentRepo: AppointmentRepo
): ViewModel() {

    private var _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> = _appointments
    // add backing fields
    val stateLiveData = MutableLiveData(State.IDLE)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            stateLiveData.postValue(State.LOADING)
            appointmentRepo.getAppointments().collect {
                _appointments.postValue(it)
                stateLiveData.postValue(State.IDLE)
            }
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch(Dispatchers.IO) {
            stateLiveData.postValue(State.LOADING)
            appointmentRepo.deleteAppointment(appointment)
            stateLiveData.postValue(State.DELETED)
        }
    }
}