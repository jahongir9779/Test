package com.superdispatch.task.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.superdispatch.task.data.ioThread
import com.superdispatch.task.models.ObjPost
import org.joda.time.DateTime
import java.util.*
import javax.inject.Singleton


@Singleton
@Database(entities = [ObjPost::class], version = 1, exportSchema = false)
abstract class PostRoomDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: PostRoomDatabase? = null
//
//        fun getInstance(context: Context): PostRoomDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(context.applicationContext, PostRoomDatabase::class.java, "Posts.db")
//                .addCallback(object : Callback() {
//                    override fun onCreate(db: SupportSQLiteDatabase) {
//                        super.onCreate(db)
//                        ioThread {
//                            getInstance(context).postDao().insertAll(make100Posts())
//                        }
//                    }
//                })
//                .build()
//
//        fun make100Posts(): List<ObjPost> {
//            var posts = ArrayList<ObjPost>()
//            for (i in 0..99) {
//                posts.add(ObjPost("Title $i", "Author $i", DateTime.now().millis))
//            }
//            return posts
//        }
//    }

}


