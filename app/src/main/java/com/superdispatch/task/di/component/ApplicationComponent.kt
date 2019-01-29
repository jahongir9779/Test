package com.superdispatch.task.di.component

import com.superdispatch.task.base.BaseApplication
import com.superdispatch.task.data.local.PostDao
import com.superdispatch.task.data.local.PostDataSourceImpl
import com.superdispatch.task.data.local.PostLocalDataSource
import com.superdispatch.task.data.local.PostRoomDatabase
import com.superdispatch.task.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {

    fun inject(application: BaseApplication)
    fun inject(postRoomDatabase: PostRoomDatabase)
    fun inject(sourceImplPost: PostDataSourceImpl)
    fun inject(postLocalDataSource: PostLocalDataSource)
    fun inject(postDao: PostDao)

}