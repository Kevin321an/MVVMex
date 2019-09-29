package com.fanfan.kevin.mvvmex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fanfan.kevin.mvvmex.UI.main.MainViewModel
import com.fanfan.kevin.mvvmex.data.CarsRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val carRepository: CarsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass){
            when {
                isAssignableFrom(MainViewModel::class.java) ->MainViewModel(carRepository)
                else->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}