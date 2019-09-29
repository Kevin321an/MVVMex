package com.fanfan.kevin.mvvmex.data.local.car

import com.fanfan.kevin.mvvmex.data.CarDataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDao
import io.reactivex.Completable
import io.reactivex.Flowable

class CarsLocalDataSource(private val carDao:CarDao) : CarDataSource {
    override fun getCars(): Flowable<List<Car>> {
       return carDao.getCars()
    }

    override fun insertorUpdateCar(car: Car): Completable {
        return carDao.insertCar(car)
    }

    override fun deleteCars() {
        carDao.dedeteAllCars()
    }
}