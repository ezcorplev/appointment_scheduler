package com.ezcorplev.appointmentschedulerapp.ui

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ezcorplev.appointmentschedulerapp.AppointmentAdapter
import com.ezcorplev.appointmentschedulerapp.OnAppointmentItemClicked
import com.ezcorplev.appointmentschedulerapp.R
import com.ezcorplev.appointmentschedulerapp.databinding.ActivityMainBinding
import com.ezcorplev.appointmentschedulerapp.models.Appointment
import com.ezcorplev.appointmentschedulerapp.utils.Consts.APPOINTMENT_BUNDLE
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

    private val TAG = this::class.java.simpleName

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        initRecyclerView()
        initUserImage()
        initClickListeners()
    }

    private fun initRecyclerView() {
        val recyclerView = binding.appointmentRecyclerView
        adapter = AppointmentAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

    }

    private fun initUserImage() {
        binding.userImageView.load(R.raw.user_pic)
    }

    private fun initObservers() {
        mainViewModel.appointments.observe(this) { appointments ->
            adapter.submitList(appointments)
//            val appointmentsPair =
//                mutableListOf<Pair<Appointment, OnAppointmentItemClicked>>()
//            appointments.forEach { appointment ->
//                appointmentsPair.add(Pair(appointment, this@MainActivity))
//            }
//            controller.setData(appointmentsPair)
//            Log.d("bla", appointments.toString())
        }

        mainViewModel.stateLiveData.observe(this) {
            // toast
            Toast.makeText(this, "Appointment Deleted!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initClickListeners() {
        binding.addAppointmentFloatingButton.setOnClickListener {
            val intent = Intent(this, AddOrEditAppointmentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onEditClicked(appointment: Appointment) {
        Log.d(TAG, "onEditClicked - $appointment")
        val intent = Intent(this, AddOrEditAppointmentActivity::class.java)
        intent.putExtra(APPOINTMENT_BUNDLE, appointment)
        startActivity(intent)
    }

    override fun onDeleteClicked(appointment: Appointment) {
        Log.d(TAG, "Appointment deletion initiated - $appointment")

        // Alert dialog before deleting
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete this appointment?")
            .setPositiveButton("Yes") { _, _ ->
                // Delete the item
                mainViewModel.deleteAppointment(appointment)
            }.setNegativeButton("No", null).show()
    }
}