package com.fanfan.kevin.mvvmex

import android.app.Application
import com.fanfan.kevin.mvvmex.di.ApplicationComponent
import com.fanfan.kevin.mvvmex.di.DaggerApplicationComponent
import com.fanfan.kevin.mvvmex.di.DaggerComponentProvider
import timber.log.Timber

class BaseApplication :Application(),DaggerComponentProvider{
    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}