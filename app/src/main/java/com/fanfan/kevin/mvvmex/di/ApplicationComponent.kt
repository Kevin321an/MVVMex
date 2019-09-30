package com.fanfan.kevin.mvvmex.di

import android.content.Context
import com.fanfan.kevin.mvvmex.UI.addCar.AddCar
import com.fanfan.kevin.mvvmex.UI.addCar.AddCarViewModel
import com.fanfan.kevin.mvvmex.UI.main.MainActivity
import com.fanfan.kevin.mvvmex.UI.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder
        fun build(): ApplicationComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(addCar: AddCar)

    fun mainViewModelFactory(): ViewModelFactoryDagger<MainViewModel>
    fun addCarViewModelFactory(): ViewModelFactoryDagger<AddCarViewModel>
}