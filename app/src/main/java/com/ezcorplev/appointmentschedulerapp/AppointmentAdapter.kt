package com.ezcorplev.appointmentschedulerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ezcorplev.appointmentschedulerapp.models.Appointment

interface OnAppointmentItemClicked {
    fun onEditClicked(appointment: Appointment)
    fun onDeleteClicked(appointment: Appointment)
}

class AppointmentAdapter(private val clickListener: OnAppointmentItemClicked?) :
    ListAdapter<Appointment, AppointmentAdapter.AppointmentViewHolder>(AppointmentDiffCallback()) {

    private class AppointmentDiffCallback : DiffUtil.ItemCallback<Appointment>() {

        override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean =
            oldItem.date == newItem.date &&
            oldItem.location == newItem.location &&
            oldItem.time == newItem.time &&
            oldItem.description == newItem.description
    }

    class AppointmentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(appointment: Appointment, clickListener: OnAppointmentItemClicked?) {
            val appointmentDate: AppCompatTextView = itemView.findViewById(R.id.appointmentDateTextView)
            val appointmentLocation: AppCompatTextView = itemView.findViewById(R.id.appointmentLocationTextView)
            val appointmentTime: AppCompatTextView = itemView.findViewById(R.id.appointmentTimeTextView)
            val appointmentDesc: AppCompatTextView = itemView.findViewById(R.id.appointmentDescTextView)
            val appointmentEditButton: AppCompatButton = itemView.findViewById(R.id.editAppointmentButton)
            val appointmentImage: AppCompatImageView = itemView.findViewById(R.id.appointmentImageView)

            appointmentDate.text = appointment.date
            appointmentTime.text = appointment.time
            appointmentDesc.text = appointment.description
            appointmentLocation.text = appointment.location

//             Handles and image loading
            appointmentImage.load(when (appointment.location) {
                "Dallas" -> R.raw.dallas
                "Memphis" -> R.raw.memphis
                "Orlando" -> R.raw.orlando
                "Park City" -> R.raw.parkcity
                "San Fransisco" -> R.raw.sf
                "St. George" -> R.raw.stgeorge
                else -> R.raw.dallas
            })

            appointmentEditButton.setOnClickListener {
                clickListener?.onEditClicked(appointment)
            }
            val appointmentDeleteButton: AppCompatButton = itemView.findViewById(R.id.deleteAppointmentButton)
            appointmentDeleteButton.setOnClickListener {
                clickListener?.onDeleteClicked(appointment)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.epoxy_model_appointment_item,
            parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bindItems(getItem(position), clickListener)

    }

    fun getAppointmentByPosition(pos: Int): Appointment {
        return getItem(pos)
    }

}