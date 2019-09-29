package com.fanfan.kevin.mvvmex.data.local.car.car

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Car::class), version = 1)

abstract class CarDatabase :RoomDatabase(){
    abstract fun carDao(): CarDao
    companion object {
        @Volatile private var INSTANCE: CarDatabase? = null
        fun getInstance(context: Context): CarDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                CarDatabase::class.java, "Sample.db")
                .build()
    }


}

