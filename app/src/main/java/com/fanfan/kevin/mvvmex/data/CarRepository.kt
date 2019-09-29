package com.fanfan.kevin.mvvmex.data

import com.fanfan.kevin.mvvmex.data.local.car.CarsLocalDataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import io.reactivex.Completable
import io.reactivex.Flowable

class CarRepository(
    private val carRemoteDataSource: CarDataSource,
    private val carsLocalDataSource: CarDataSource
) : CarsRepository {
    override fun getCars(): Flowable<List<Car>> {
       return carsLocalDataSource.getCars()
    }

    override fun insertUpdateCar(car: Car): Completable {
        return carsLocalDataSource.insertorUpdateCar(car)
    }

    override fun deleteCars() {
        carsLocalDataSource.deleteCars()
    }
}