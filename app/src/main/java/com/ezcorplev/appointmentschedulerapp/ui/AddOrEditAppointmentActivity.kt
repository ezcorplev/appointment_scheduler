package com.ezcorplev.appointmentschedulerapp.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ezcorplev.appointmentschedulerapp.R
import com.ezcorplev.appointmentschedulerapp.databinding.ActivityAddOrEditAppointmentBinding
import com.ezcorplev.appointmentschedulerapp.enums.AppointmentLocation
import com.ezcorplev.appointmentschedulerapp.enums.State
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.utils.Consts.APPOINTMENT_BUNDLE
import com.ezcorplev.appointmentschedulerapp.viewmodels.AddOrEditAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddOrEditAppointmentActivity : AppCompatActivity() {

    private val addOrEditAppointmentViewModel: AddOrEditAppointmentViewModel by viewModels()

    private lateinit var binding: ActivityAddOrEditAppointmentBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrEditAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appointment = intent?.getSerializableExtra(APPOINTMENT_BUNDLE) as? Appointment

        setupCalendar(appointment)
        setupTime(appointment)
        setupLocation(appointment)
        setupDescription(appointment)

        setupConfirmationBtn(appointment)
//        if (appointment == null) {
//            // No appointment extra, create new appointment
//            initClickListeners()
//        } else {
//            // Edit mode, prefill appointment details
////            prefillAppointmentDetails()
////            initClickListenersForEdit(appointment)
//            prefill(appointment)
//        }

        initObservers()
    }

    private fun setupConfirmationBtn(appointment: Appointment?) {
        binding.confirmAppointmentBtn.setOnClickListener {
            val appointmentLocation = AppointmentLocation.DALLAS
            val appointmentId = appointment?.id ?: System.currentTimeMillis()
            val appointmentDesc = binding.AppointmentDescriptionET.text.toString()
            val currentAppointment = Appointment(
                id = appointmentId,
                date = extractDateFromDatePicker(),
                time = extractTimeFromTimePicker(),
                location = binding.appointmentLocationSpinner.selectedItem as AppointmentLocation, // will this work?
//                location = appointmentLocation
                description = appointmentDesc,
            )

            addOrEditAppointmentViewModel.addOrEditAppointment(currentAppointment)
        }
    }

    private fun extractDateFromDatePicker(): Date {
        val yearString = binding.appointmentDateDp.year.toString()
        val monthString = (binding.appointmentDateDp.month + 1).toString() // Note that the month is 0-indexed, so add 1 to get the correct month number
        val dayString = binding.appointmentDateDp.dayOfMonth.toString()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return sdf.parse("$yearString-$monthString-$dayString")
    }

    private fun setupCalendar(appointment: Appointment?) {
        appointment?.let {
            val cal = Calendar.getInstance()
            cal.time = it.date
            binding.appointmentDateDp.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        }
    }

    private fun extractTimeFromTimePicker(): Date {
        val hourString = binding.appointmentTimeTP.hour.toString() // sets hour
        val minuteString = binding.appointmentTimeTP.minute.toString() // sets minute
        val amPm = if (hourString.toInt() < 12) "AM" else "PM" // sets am pm
        val time = SimpleDateFormat("HH-mm a", Locale.ENGLISH) // sets format
        return time.parse("$hourString:$minuteString $amPm") // returns time in format

    }

    private fun setupTime(appointment: Appointment?) {
        appointment?.let { // if appointment is not null
            val cal = Calendar.getInstance() // set a calendar
            cal.time = it.time // get the hour
            binding.appointmentTimeTP.hour = cal.get(Calendar.HOUR_OF_DAY) // set the hour
            binding.appointmentTimeTP.minute = cal.get(Calendar.MINUTE) // set the minute
        }
    }

    private fun setupLocation(appointment: Appointment?) {
        val locations = arrayListOf(AppointmentLocation.values().toString())
        val locationSpinner = binding.appointmentLocationSpinner // access spinner
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource( // setup adapter
            this,
            R.array.locations,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        locationSpinner.adapter = adapter // attach adapter
        appointment?.let {

        }

    }

    private fun setupDescription(appointment: Appointment?) {
        appointment?.let {
            binding.AppointmentDescriptionET.setText(appointment.description)
        }

    }

    private fun initObservers() {
        addOrEditAppointmentViewModel.stateLiveData.observe(this) {
            when (it) {
                State.LOADING -> {}// Show progress bar
                State.ADDED,
                State.EDITED -> finish()
                else -> {} // Do nothing for now
            }
        }
    }

    private fun initClickListeners() {
        val datePicker = binding.appointmentDateDp
        var appointmentLocation = ""
//
//        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth) { view, year, monthOfYear, dayOfMonth ->
//            // Extract the year, month, and day from the DatePicker
//            val yearString = year.toString()
//            val monthString =
//                (monthOfYear + 1).toString() // Note that the month is 0-indexed, so add 1 to get the correct month number
//            val dayString = dayOfMonth.toString()
//            dateString = "$yearString-$monthString-$dayString" // Format the date as a string
//        }
//
//        val timePicker = binding.appointmentTimeTP
//        val hour = timePicker.hour // gets the selected hour
//        val minute = timePicker.minute // gets the selected minute
//        val amPm = if (timePicker.hour < 12) "AM" else "PM" // determines whether it's AM or PM based on the selected hour
//        timeString = String.format("%02d:%02d %s", hour, minute, amPm) // converts the hour, minute, and amPm values into a string in the format "hh:mm AM/PM"
//
//
//        val locationSpinner = binding.appointmentLocationSpinner // access spinner
//        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource( // attach adapter
//            this,
//            R.array.locations,
//            android.R.layout.simple_spinner_item
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        locationSpinner.adapter = adapter
//
//        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                selectedLocation = parent?.getItemAtPosition(position).toString()
//                // update the location property of the appointment object
//                appointmentLocation = selectedLocation
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // do nothing
//            }
//        }
//
//        binding.confirmAppointmentBtn.setOnClickListener {
//            val appointmentId = System.currentTimeMillis()
//            val appointmentDesc = binding.AppointmentDescriptionET.text.toString()
//            val appointment = Appointment(
//                id = appointmentId,
//                date = dateString,
//                time = timeString,
//                location = appointmentLocation,
//                description = appointmentDesc,
//            )
//
//            addOrEditAppointmentViewModel.addOrEditAppointment(appointment)
//        }
    }


}