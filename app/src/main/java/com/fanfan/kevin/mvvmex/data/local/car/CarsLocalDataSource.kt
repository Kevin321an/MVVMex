package com.fanfan.kevin.mvvmex.data.local.car

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.fanfan.kevin.mvvmex.data.CarDataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDao
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CarsLocalDataSource @Inject constructor(private val carDao:CarDao) : CarDataSource {
    override fun getCars(): Flowable<List<Car>> {
       return carDao.getCars()
    }

    override fun getCarsDataSource(): DataSource.Factory<Int, Car> {
        return carDao.getCarcDataSourceF()
    }

    override fun insertorUpdateCar(car: Car): Completable {
        return carDao.insertCar(car)
    }

    override fun deleteCars() {
        carDao.dedeteAllCars()
    }
}