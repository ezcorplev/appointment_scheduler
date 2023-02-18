package com.ezcorplev.appointmentschedulerapp

import android.widget.ArrayAdapter
import android.widget.Button
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.ezcorplev.appointmentschedulerapp.databinding.EpoxyModelAppointmentItemBinding
import com.ezcorplev.appointmentschedulerapp.epoxy.ViewBindingKotlinModel
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.ui.MainActivity

//interface OnAppointmentItemClicked {
//    fun onEditClicked(appointment: Appointment)
//    fun onDeleteClicked(appointment: Appointment)
//}

data class AppointmentEpoxyModel(val appointment: Appointment?, val clickListener: OnAppointmentItemClicked?):

    ViewBindingKotlinModel<EpoxyModelAppointmentItemBinding>(R.layout.epoxy_model_appointment_item) {

    override fun EpoxyModelAppointmentItemBinding.bind() {

        appointment?.let { appointment ->

            // Sets appointment text attributes

            appointmentDateTextView.text = appointment.date
            appointmentTimeTextView.text = appointment.time
            appointmentDescTextView.text = appointment.description

            editAppointmentButton.setOnClickListener {
                clickListener?.onEditClicked(appointment)
            }

            deleteAppointmentButton.setOnClickListener {
                clickListener?.onDeleteClicked(appointment)
            }


            appointmentLocationTextView.text = appointment.location

//             Handles progressBar and image loading
             val appointmentImage = when (appointment.location) {
             "Dallas" -> R.raw.dallas
             "Memphis" -> R.raw.memphis
             "Orlando" -> R.raw.orlando
             "Park City" -> R.raw.parkcity
             "San Fransisco" -> R.raw.sf
             "St. George" -> R.raw.stgeorge

             else -> R.raw.dallas
             }

        }
//            ?: shimmerLayout.startShimmer() // if product is null, start shimmer

    }
}