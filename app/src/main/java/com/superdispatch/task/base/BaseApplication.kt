package com.superdispatch.task.base

import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.superdispatch.task.BuildConfig
import com.superdispatch.task.data.local.PostRoomDatabase
import com.superdispatch.task.di.component.ApplicationComponent
import com.superdispatch.task.di.component.DaggerApplicationComponent
import com.superdispatch.task.di.module.ApplicationModule


/**
 * Created by jahon on 13-Mar-18.
 */

class BaseApplication : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    fun setup() {
        component = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        component.inject(this)





    }

    companion object {
        var udid = ""
        lateinit var component: ApplicationComponent
        lateinit var instance: BaseApplication private set
    }

}
