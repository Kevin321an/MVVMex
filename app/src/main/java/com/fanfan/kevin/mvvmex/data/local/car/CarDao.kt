package com.fanfan.kevin.mvvmex.data.local.car.car

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CarDao {
    @Query("SELECT * FROM CARS WHERE carname = :name")
    fun getCarByName(name: String): Flowable<Car>


    @Query("SELECT * FROM CARS")
    fun getCars(): Flowable<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCar(car: Car): Completable

    @Query("DELETE FROM cars")
    fun dedeteAllCars()
}