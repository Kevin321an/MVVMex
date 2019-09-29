package com.fanfan.kevin.mvvmex.UI.addCar

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.fanfan.kevin.mvvmex.R
import com.fanfan.kevin.mvvmex.UI.main.MainActivity
import com.fanfan.kevin.mvvmex.ViewModelFactory
import com.fanfan.kevin.mvvmex.data.local.car.car.carBuilder
import com.fanfan.kevin.mvvmex.di.Injection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_add_car.*
import kotlinx.android.synthetic.main.content_add_car.*
import kotlinx.android.synthetic.main.content_main.*

class AddCar : AppCompatActivity() {

    private lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory).get(AddCarViewModel::class.java)
    }
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        setSupportActionBar(toolbar)

        viewModelFactory = Injection.provideMainViewModelFactory(this) //TODO should be different provider

        fab.setOnClickListener { view ->
            disposable.add(
                viewModel.updateOrAddCar(
                    carBuilder {year = this@AddCar.car_name_addact.text.toString()
                    carName= this@AddCar.car_year_addact.text.toString() })
                    .subscribeOn(Schedulers.io())
                    .doOnComplete {  this@AddCar.finish() }
                    .subscribe ({ Log.e(TAG,"add success ${this@AddCar.car_name_addact.text}  year: ${this@AddCar.car_year_addact.text}")},
                        { error-> Log.e(TAG,"unable to add the car  ${this@AddCar.car_name_addact.text}  year: ${this@AddCar.car_year_addact.text} $error")})

            )

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
