package com.ezcorplev.appointmentschedulerapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ezcorplev.appointmentschedulerapp.models.Appointment

@Database(entities = [
    Appointment::class
 ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao

}