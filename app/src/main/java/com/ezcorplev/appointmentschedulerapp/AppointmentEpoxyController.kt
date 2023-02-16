package com.ezcorplev.appointmentschedulerapp

import com.airbnb.epoxy.TypedEpoxyController
import com.ezcorplev.appointmentschedulerapp.models.Appointment

class AppointmentEpoxyController:TypedEpoxyController<List<Pair<Appointment, OnAppointmentItemClicked?>>>() {

    override fun buildModels(data: List<Pair<Appointment, OnAppointmentItemClicked?>>?) {
        if (data.isNullOrEmpty()) {
            repeat(7) {
                val epoxyId = it + 1
                AppointmentEpoxyModel(null, null).id(epoxyId).addTo(this)
            }
            // todo loading state
            return
        }

        data.forEach { appointment ->
            AppointmentEpoxyModel(appointment.first, appointment.second).id(appointment.first.id).addTo(this)
        }
    }
}