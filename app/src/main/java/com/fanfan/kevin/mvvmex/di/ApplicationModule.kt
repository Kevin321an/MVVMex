package com.fanfan.kevin.mvvmex.di

import android.content.Context
import com.fanfan.kevin.mvvmex.ViewModelFactory
import com.fanfan.kevin.mvvmex.data.CarDataSource
import com.fanfan.kevin.mvvmex.data.CarRepository
import com.fanfan.kevin.mvvmex.data.CarsRepository
import com.fanfan.kevin.mvvmex.data.local.car.CarsLocalDataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDao
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {
    @JvmStatic @Provides
    @Singleton
    fun provideDB(context: Context): CarDatabase {
        return CarDatabase.getInstance(context)
    }
    @JvmStatic @Provides @Singleton
    fun provideCarDao(context: Context): CarDao {
        return provideDB(context).carDao()
    }
    @JvmStatic @Provides @Singleton
    fun provideCarLocalDataSource(context: Context): CarDataSource {
        return CarsLocalDataSource(provideCarDao(context))
    }
    @JvmStatic @Provides @Singleton
    fun provideCarsRepository(context: Context): CarsRepository {
        val carsLocalDataSource = provideCarLocalDataSource(context);
        return CarRepository(carsLocalDataSource,carsLocalDataSource )
    }
    @JvmStatic @Provides @Singleton
    fun provideMainViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(provideCarsRepository(context))
    }
}
