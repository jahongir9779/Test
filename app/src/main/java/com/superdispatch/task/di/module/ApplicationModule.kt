package com.superdispatch.task.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.superdispatch.task.base.BaseApplication
import com.superdispatch.task.data.local.PostDao
import com.superdispatch.task.data.local.PostDataSourceImpl
import com.superdispatch.task.data.local.PostLocalDataSource
import com.superdispatch.task.data.local.PostRoomDatabase
import com.superdispatch.task.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val baseApp: BaseApplication) {


    @Provides
    fun providesAppContext(): Context {
        return baseApp.applicationContext
    }


    @Provides
    @Singleton
    @PerApplication
    fun provideApplication(): Application {
        return baseApp
    }


    @Provides
    @Singleton
    fun providepostDataSource(): PostLocalDataSource {
        return PostDataSourceImpl()
    }


    @Provides
    @Singleton
    fun providePostRoomDatabase(): PostRoomDatabase {
        return PostRoomDatabase.getInstance(baseApp)

//        return Room.databaseBuilder(baseApp, PostRoomDatabase::class.java, "Posts.db")
//            .allowMainThreadQueries()
            // prepopulate the database after onCreate was called
//            .addCallback(object : RoomDatabase.Callback() {
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    // insert the data on the IO Thread
//                    super.onCreate(db)
////                    ioThread {
////                        postDao().insertAll(make100Posts())
////                    }
//                }
//            })
//            .build()
    }


    @Provides
    @Singleton
    fun providesDataRepository(postRoomDatabase: PostRoomDatabase): PostDao {
        return postRoomDatabase.postDao()
    }


}