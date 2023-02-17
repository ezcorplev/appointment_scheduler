package com.ezcorplev.appointmentschedulerapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ezcorplev.appointmentschedulerapp.R
import com.ezcorplev.appointmentschedulerapp.databinding.ActivityAddOrEditAppointmentBinding
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.viewmodels.AddOrEditAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddOrEditAppointmentActivity() : AppCompatActivity() {

    private val addOrEditAppointmentViewModel: AddOrEditAppointmentViewModel by viewModels()

    private lateinit var binding: ActivityAddOrEditAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrEditAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListeners()

    }

    private fun initClickListeners() {

        val appointmentId = System.currentTimeMillis()

        val datePicker = binding.appointmentDateDP
        var dateString = ""
        var appointmentLocation = ""

        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth) { view, year, monthOfYear, dayOfMonth ->
            // Extract the year, month, and day from the DatePicker
            val yearString = year.toString()
            val monthString =
                (monthOfYear + 1).toString() // Note that the month is 0-indexed, so add 1 to get the correct month number
            val dayString = dayOfMonth.toString()
            dateString = "$yearString-$monthString-$dayString" // Format the date as a string
        }

        val timePicker = binding.appointmentTimeTP
        val hour = timePicker.hour // gets the selected hour
        val minute = timePicker.minute // gets the selected minute
        val amPm = if (timePicker.hour < 12) "AM" else "PM" // determines whether it's AM or PM based on the selected hour
        val timeString = String.format("%02d:%02d %s", hour, minute, amPm) // converts the hour, minute, and amPm values into a string in the format "hh:mm AM/PM"


        val locationSpinner = binding.appointmentLocationSpinner
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.locations,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        locationSpinner.adapter = adapter

        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLocation = parent?.getItemAtPosition(position).toString()
                // update the location property of the appointment object
                appointmentLocation = selectedLocation.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }
        }

        binding.confirmAppointmentBtn.setOnClickListener {

            val appointmentDesc = binding.AppointmentDescriptionET.text.toString()

            lifecycleScope.launchWhenStarted {

                val appointment = Appointment(
                    id = appointmentId,
                    date = dateString,
                    time = timeString,
                    location = appointmentLocation,
                    description = appointmentDesc,
                )

                addOrEditAppointmentViewModel.addOrEditAppointment(
                    appointment.id,
                    appointment.date,
                    appointment.time,
                    appointment.location,
                    appointment.description
                )
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}