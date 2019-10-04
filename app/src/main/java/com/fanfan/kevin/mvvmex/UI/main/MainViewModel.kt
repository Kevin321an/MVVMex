package com.fanfan.kevin.mvvmex.UI.main

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.fanfan.kevin.mvvmex.data.CarsRepository
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import com.fanfan.kevin.mvvmex.data.local.car.car.carBuilder
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class MainViewModel @Inject constructor(private val carsRepository: CarsRepository) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 30
        private const val ENABLE_PLACEHOLDERS = true
    }

    fun carName(): Flowable<Car> {
        return carsRepository.getCars()
            .map { cars -> if (cars.isEmpty()) carBuilder { } else cars[cars.lastIndex] }
    }

    fun allCars(): Flowable<List<Car>> {
        return carsRepository.getCars()
    }

    val allCars = RxPagedListBuilder(carsRepository.getCarsDataSource(), PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
        .build())
        .buildObservable()


    fun updateOrAddCar(car: Car): Completable {
        return Completable.fromAction { carsRepository.insertUpdateCar(car) }
    }

    fun deleteCars() {
        carsRepository.deleteCars()
    }

}