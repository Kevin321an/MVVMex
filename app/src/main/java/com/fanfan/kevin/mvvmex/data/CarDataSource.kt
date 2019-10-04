package com.fanfan.kevin.mvvmex.data

import androidx.paging.DataSource
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import io.reactivex.Completable
import io.reactivex.Flowable

/***
 * the reason to have both DataSource/Repository interface is to decouple the parameter input
 */
interface CarDataSource {

    fun getCars(): Flowable<List<Car>>

    fun insertorUpdateCar(car: Car): Completable

    fun deleteCars()

    fun getCarsDataSource(): DataSource.Factory<Int,Car>

}