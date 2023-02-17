package com.ezcorplev.appointmentschedulerapp.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.ezcorplev.appointmentschedulerapp.AppointmentEpoxyController
import com.ezcorplev.appointmentschedulerapp.OnAppointmentItemClicked
import com.ezcorplev.appointmentschedulerapp.R
import com.ezcorplev.appointmentschedulerapp.databinding.ActivityMainBinding
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.ui.Consts.APPOINTMENT_BUNDLE
import com.ezcorplev.appointmentschedulerapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

//Create a small Android mobile app that allows you to schedule personal appointments.
//I would like to create appointments with a date, time, location, and description. The location
// should be a dropdown/select with the following options: San Diego, St. George, Park City, Dallas,
// Memphis, and Orlando
// -> class Appointment (date, time, location, desc) + enum class AppointmentLocation for dropdown
//I would like to see a list of my appointments
// -> Epoxy for appointments in cardViews?
//I would like to edit my appointments
// -> onClick -> edit appointment?
//I would like to be able to cancel (delete) an appointment
// -> onSwipe/onClick -> delete appointment?
//I would like the user interface to be simple yet elegant (i.e., has some quick, light styling)
//Submit a link to a Github repo with your solution.
// display List<Appointment>> on epoxy - done
// add images for locations (svg / png) -
// setup onClick addAppointment - done
// create activity screen for addOrEditAppointment - done, only addAppointment for now
// setup onClick deleteAppointment - done
// setup onClick editAppointment -
//


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnAppointmentItemClicked {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val controller = AppointmentEpoxyController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUserImageAndText()
        initClickListeners()
        initEpoxy()
        initObservers()

    }

    private fun initUserImageAndText() {
        val sampleText = "Welcome back user, here are your appointments..."
        binding.userImageView.load(R.raw.user_pic)
        binding.userTextView.text = sampleText
    }

    private fun initEpoxy() {
        binding.epoxyRecyclerView.setController(controller)
    }

    private fun initObservers() {

        lifecycleScope.launchWhenStarted {
            mainViewModel.appointments.collect { appointments ->
                val appointmentsPair =
                    mutableListOf<Pair<Appointment, OnAppointmentItemClicked>>()
                appointments.forEach { appointment ->
                    appointmentsPair.add(Pair(appointment, this@MainActivity))
                }
                controller.setData(appointmentsPair)
                Log.d("bla", appointments.toString())
            }
        }

        mainViewModel.onAppointmentDeleted.observe(this) {
            // toast
            Toast.makeText(this, "Appointment Deleted!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initClickListeners() {

        binding.addAppointmentFloatingButton.setOnClickListener {
            val intent = Intent(this, AddOrEditAppointmentActivity::class.java)
//            intent.putExtra<Appointment?>(APPOINTMENT_BUNDLE, null) // const file TAG
            startActivity(intent)
        }

    }

    companion object {
        private val TAG = "${this::class.java.simpleName}TAG"
    }

    override fun onEditClicked(appointment: Appointment) {
        Log.d(TAG, "onEditClicked - $appointment")
        // intent -> addOrEdit putExtra Appointment
    }

    override fun onDeleteClicked(appointment: Appointment) {
        Log.d(TAG, "onDeleteClicked - $appointment")

        // dialog box before deleting
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this appointment?")
        builder.setPositiveButton("Yes") { dialog, which ->
            // Delete the item
            mainViewModel.deleteAppointment(appointment)
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Do nothing
        }
        builder.show()
    }

}