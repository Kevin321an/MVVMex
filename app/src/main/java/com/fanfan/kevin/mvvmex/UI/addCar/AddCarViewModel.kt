package com.fanfan.kevin.mvvmex.UI.addCar

import androidx.lifecycle.ViewModel
import com.fanfan.kevin.mvvmex.data.CarsRepository
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import com.fanfan.kevin.mvvmex.data.local.car.car.carBuilder
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class AddCarViewModel @Inject constructor (private val carsRepository: CarsRepository) : ViewModel() {

    fun carName(): Flowable<Car> {
        return carsRepository.getCars().map { cars->if(cars.isEmpty()) carBuilder {  } else cars[cars.lastIndex] }
    }

    fun updateOrAddCar(car: Car): Completable {
        return carsRepository.insertUpdateCar(car)
    }

    fun deleteCars() {
        carsRepository.deleteCars()
    }

}