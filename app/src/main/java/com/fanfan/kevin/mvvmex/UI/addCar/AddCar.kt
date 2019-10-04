package com.fanfan.kevin.mvvmex.UI.addCar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
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
import android.view.MotionEvent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.inputmethod.InputMethodManager
import com.fanfan.kevin.mvvmex.di.ViewModelFactoryDagger
import com.fanfan.kevin.mvvmex.di.injector
import javax.inject.Inject


class AddCar : AppCompatActivity() {

    //    private lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var viewModelFactory: ViewModelFactoryDagger<AddCarViewModel>
    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddCarViewModel::class.java)
    }
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        setSupportActionBar(toolbar)

//        viewModelFactory = Injection.provideMainViewModelFactory(this) //TODO should be different provider

        injector.inject(this)
//        add_content_layout.setOnClickListener( )
        add_content_layout.setOnTouchListener { v, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            true
        }

        fab.setOnClickListener { view ->
            disposable.add(
                viewModel.updateOrAddCar(
                    carBuilder {
                        carName = this@AddCar.car_name_addact.text.toString()
                        year = this@AddCar.car_year_addact.text.toString()
                    })
                    .subscribeOn(Schedulers.io())
                    .doOnComplete { this@AddCar.finish() }
                    .subscribe({
                        Log.e(
                            TAG,
                            "add success ${this@AddCar.car_name_addact.text}  year: ${this@AddCar.car_year_addact.text}"
                        )
                    },
                        { error ->
                            Log.e(
                                TAG,
                                "unable to add the car  ${this@AddCar.car_name_addact.text}  year: ${this@AddCar.car_year_addact.text} $error"
                            )
                        })

            )

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
