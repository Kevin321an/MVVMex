package com.fanfan.kevin.mvvmex

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fanfan.kevin.mvvmex.UI.main.MainViewModel
import com.fanfan.kevin.mvvmex.data.CarsRepository
import com.fanfan.kevin.mvvmex.data.local.car.car.Car
import com.fanfan.kevin.mvvmex.data.local.car.car.CarBuilder
import com.fanfan.kevin.mvvmex.data.local.car.car.CarDao
import com.fanfan.kevin.mvvmex.data.local.car.car.carBuilder
import io.reactivex.Flowable
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

class MainViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var carsRepository: CarsRepository

    @Mock
    private lateinit var carDao: CarDao

    @Captor
    private lateinit var carArgumentCaptor: ArgumentCaptor<Car>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(carsRepository)
    }

    @Test
    fun getCarName_whenNoCarSaved() {
        Mockito.`when`(carsRepository.getCars()).thenReturn(Flowable.empty<List<Car>>())

        viewModel.allCars()
            .test()
            .assertNoValues()
    }

    @Test
    fun getCar_whenCarSaved() {
        val cars = listOf<Car>(carBuilder { carName = "car name"  }, carBuilder { carName = "car name"  })
        Mockito.`when`(carsRepository.getCars()).thenReturn(Flowable.just(cars))

        //When getting the car name
        viewModel.allCars()
            .test()
            // The correct car name is emitted
            .assertValue(cars)
    }

    @Test
    fun updateCarName_updatesNameInDataSource() {
        // When updating the car name
        viewModel.updateOrAddCar(carBuilder { carName = "new car name" })
            .test()
            .assertComplete()

        // The car name is updated in the data source
        // using ?: Car("someCar") because otherwise, we get
        // "IllegalStateException: carArgumentCaptor.capture() must not be null"
        Mockito.verify<CarsRepository>(carsRepository).insertUpdateCar(capture(carArgumentCaptor))
        MatcherAssert.assertThat(carArgumentCaptor.value.carName, Matchers.`is`("new car name"))
    }

}
