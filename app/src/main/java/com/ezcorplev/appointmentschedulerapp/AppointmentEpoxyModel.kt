package com.ezcorplev.appointmentschedulerapp

import android.widget.Button
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.ezcorplev.appointmentschedulerapp.databinding.EpoxyModelAppointmentItemBinding
import com.ezcorplev.appointmentschedulerapp.epoxy.ViewBindingKotlinModel
import com.ezcorplev.appointmentschedulerapp.models.Appointment

interface OnAppointmentItemClicked {
    fun onEditClicked(appointment: Appointment)
    fun onDeleteClicked(appointment: Appointment)
}

data class AppointmentEpoxyModel(val appointment: Appointment?, val clickListener: OnAppointmentItemClicked?):

    ViewBindingKotlinModel<EpoxyModelAppointmentItemBinding>(R.layout.epoxy_model_appointment_item) {

//    val deleteButton: Button by bind(R.id.deleteAppointmentButton)

    override fun EpoxyModelAppointmentItemBinding.bind() {

//        shimmerLayout.isVisible = product == null // if product is null, show shimmer layout
//        cardView.isInvisible = appointment == null // if product is null, hide product card view

        appointment?.let { appointment ->
//            shimmerLayout.stopShimmer() // once product loads, stop shimmer and load product ->

            // Sets product text attributes

            appointmentDateTextView.text = appointment.date
            appointmentTimeTextView.text = appointment.time
            appointmentDescTextView.text = appointment.description

            editAppointmentButton.setOnClickListener {
                clickListener?.onEditClicked(appointment)
            }

            deleteAppointmentButton.setOnClickListener {
                clickListener?.onDeleteClicked(appointment)
            }
//            appointmentDateTextView.text = appointment.location

            // Handles progressBar and image loading
            // val appointmentImage = when (Appointment.location) {
            // is enum1 -> R.Drawable.imageSF
            // is enum2 -> R.Drawable.imageSG
            // else -> R.Drawable.someImage
            // }

//            appointmentImageViewLoadingProgressBar.isVisible = true // learn about disk strategy (caching for images)
//            appointmentImageView.load(appointment.image) {
//                listener { request, result ->
//                    appointmentImageViewLoadingProgressBar.isGone = true
//                }
//            }

        }
//            ?: shimmerLayout.startShimmer() // if product is null, start shimmer

    }
}