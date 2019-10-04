package com.fanfan.kevin.mvvmex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry

import com.fanfan.kevin.mvvmex.data.local.car.CarsLocalDataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDatabase
import com.fanfan.kevin.mvvmex.data.local.car.car.carBuilder

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Integration tests for the [com.fanfan.kevin.mvvmex.data.local.car.CarsLocalDataSource] implementation with Room.
 */
class CarsLocalDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var mDatabase: CarDatabase? = null
    private var mDataSource: CarsLocalDataSource? = null

    @Before
    @Throws(Exception::class)
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            CarDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
        mDataSource = CarsLocalDataSource(mDatabase!!.carDao())
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase!!.close()
    }

    @Test
    fun insertAndGetCar() {
        mDataSource!!.insertorUpdateCar(CAR).subscribe()
        mDataSource!!.getCars()
            .test()
            .assertValue { cars -> cars.size == 1 }
    }

    @Test
    fun updateAndGetCar() {
        mDataSource!!.insertorUpdateCar(CAR).subscribe()
        val updateCar = carBuilder {  carName = CAR.carName; year = "new year"}
        mDataSource!!.insertorUpdateCar(updateCar).subscribe()
//        mDataSource.getCarsByName(Car.carName).test().assertValue{it[0].carName ==  updatedCar.carName && it.size ==2}

    }

    @Test
    fun deleteAndGetCar() {
        mDataSource!!.insertorUpdateCar(CAR)
        mDataSource!!.deleteCars()
        mDataSource!!.getCars().test().assertValue (emptyList())
    }

    companion object {
        private val CAR = carBuilder { carName = "Honda Civic"; year = "2012" }
        private val CAR2 = Car ("1" ,"Honda Civic", "2012" )
    }
}
