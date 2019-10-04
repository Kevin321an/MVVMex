package com.fanfan.kevin.mvvmex.UI.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.fanfan.kevin.mvvmex.R
import com.fanfan.kevin.mvvmex.UI.addCar.AddCar
import com.fanfan.kevin.mvvmex.di.ViewModelFactoryDagger
import com.fanfan.kevin.mvvmex.di.injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

//    private lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var viewModelFactory: ViewModelFactoryDagger<MainViewModel>
    //    private val viewModel :MainViewModel by viewModels()
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val disposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

//        viewModelFactory = Injection.provideMainViewModelFactory(this)
        injector.inject(this)


        fab.setOnClickListener { view ->
            viewModel.allCars().subscribe({Log.e(TAG,"all cars size ${it.size}")})
            startAddCarAct()

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

        }
    }

    fun startAddCarAct() {
        val intent = Intent(this, AddCar::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val adapter = MainlistAdapter()
        carList.adapter = adapter
        disposable.add(
            viewModel.allCars
                .subscribe {adapter.submitList(it)
                    Timber.d(TAG,"allCars size is ${it.size}")}
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
