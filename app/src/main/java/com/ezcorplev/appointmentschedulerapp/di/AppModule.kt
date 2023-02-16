package com.ezcorplev.appointmentschedulerapp.di

import android.content.Context
import androidx.room.Room
import com.ezcorplev.appointmentschedulerapp.db.AppDatabase
import com.ezcorplev.appointmentschedulerapp.repos.AppointmentRepo
import com.ezcorplev.appointmentschedulerapp.repos.AppointmentRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesAppointmentRepo(db: AppDatabase): AppointmentRepo = AppointmentRepoImpl(db)

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "MovemedicalDB"
    ).build()
}