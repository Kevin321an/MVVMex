package com.fanfan.kevin.mvvmex
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDatabase
import com.fanfan.kevin.mvvmex.data.local.car.car.carBuilder
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CarDatabase

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            CarDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getCarWhenNoCarInserted() {
        database.carDao().getCarByName("123")
            .test()
            .assertValue(emptyList())
    }

    @Test
    fun insertAndGetCar2() {
        database.carDao().insertCar(CAR2).subscribe()
        database.carDao().getCarById(CAR2.id).test().assertValue{it.carName ==CAR2.carName && it.id == CAR2.id}
    }

    @Test
    fun updateAndGetCar() {
        database.carDao().insertCar(CAR).subscribe()
        val updatedCar = carBuilder {  carName = CAR.carName; year = "new year"}
        database.carDao().insertCar(updatedCar).subscribe()
        database.carDao().getCarByName(CAR.carName)
            .test()
            .assertValue { it[0].carName ==  updatedCar.carName && it.size ==2}
    }

    @Test
    fun deleteAndGetCar() {
        database.carDao().insertCar(CAR)
        database.carDao().dedeteAllCars()
        database.carDao().getCars()
            .test()
            .assertValue(emptyList())
    }

    companion object {
        private val CAR = carBuilder { carName = "Honda Civic"; year = "2012" }
        private val CAR2 = Car ("1" ,"Honda Civic", "2012" )
    }
}