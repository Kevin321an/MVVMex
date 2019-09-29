package com.fanfan.kevin.mvvmex.data

import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import io.reactivex.Completable
import io.reactivex.Flowable

interface CarsRepository {

    fun getCars(): Flowable<List<Car>>

    fun insertUpdateCar(car:Car):Completable

    fun deleteCars()

}