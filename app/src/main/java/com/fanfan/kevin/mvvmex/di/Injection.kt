package com.fanfan.kevin.mvvmex.di

import android.content.Context
import com.fanfan.kevin.mvvmex.ViewModelFactory
import com.fanfan.kevin.mvvmex.data.CarRepository
import com.fanfan.kevin.mvvmex.data.CarsRepository
import com.fanfan.kevin.mvvmex.data.local.car.CarsLocalDataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDao
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDatabase

object Injection{

    fun provideDB(context: Context): CarDatabase {
        return CarDatabase.getInstance(context)
    }
    fun provideCarDao(context: Context): CarDao {
        return provideDB(context).carDao()
    }
    fun provideCarLocalDataSource(context: Context): CarsLocalDataSource {
        return CarsLocalDataSource(provideCarDao(context))
    }
    fun provideCarsRepository(context: Context): CarRepository {
        val carsLocalDataSource = provideCarLocalDataSource(context);
        return CarRepository(carsLocalDataSource,carsLocalDataSource )
    }
    fun provideMainViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(provideCarsRepository(context))
    }
}