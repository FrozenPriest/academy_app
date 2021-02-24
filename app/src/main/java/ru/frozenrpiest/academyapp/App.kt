package ru.frozenrpiest.academyapp

import android.app.Application
import androidx.room.Room
import ru.frozenrpiest.academyapp.data.local.MoviesRoomDatabase

class App : Application() {
    companion object {
        lateinit var database: MoviesRoomDatabase

    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            MoviesRoomDatabase::class.java,
            MoviesRoomDatabase.DATABASE_NAME
        )
            .build()
    }
}