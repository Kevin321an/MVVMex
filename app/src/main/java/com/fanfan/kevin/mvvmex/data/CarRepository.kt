package com.fanfan.kevin.mvvmex.data

import androidx.paging.DataSource
import com.fanfan.kevin.mvvmex.data.local.car.CarsLocalDataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CarRepository @Inject constructor(
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

    override fun getCarsDataSource(): DataSource.Factory<Int, Car> {

            return carsLocalDataSource.getCarsDataSource()

    }
}