package com.superdispatch.task.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.superdispatch.task.models.ObjPost
import io.reactivex.Single


@Dao
interface PostDao {
    @Query("SELECT * FROM post_table  ORDER BY id DESC")
    fun getAll(): Single<List<ObjPost>>


    @Query("SELECT * FROM post_table  WHERE id > :firstVisiblePostId  ORDER BY id DESC")
    fun getFreshPosts(firstVisiblePostId: Int): DataSource.Factory<Int, ObjPost>

    @Query("SELECT * FROM post_table  WHERE id > :firstVisiblePostId  ORDER BY id DESC")
    fun getAllFreshPosts(firstVisiblePostId: Int): Single<List<ObjPost>>

    @Query("SELECT * FROM post_table ORDER BY id DESC limit :requestedLoadSize")
    fun getPosts(requestedLoadSize: Int): Single<List<ObjPost>>

    @Query("SELECT * FROM post_table WHERE id < :lastVisibleItemId ORDER BY id DESC limit :requestedLoadSize")
    fun getPostsAfter(lastVisibleItemId: Int, requestedLoadSize: Int): Single<List<ObjPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: ObjPost)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<ObjPost>)

    @Update
    fun update(post: ObjPost)

    @Delete
    fun delete(posts: ObjPost)


    @Query("DELETE FROM post_table ")
    fun clearTable()

    @Query("SELECT * FROM  post_table WHERE ID = (SELECT MAX(ID)  FROM post_table)")
    fun getLastItem(): ObjPost

//    @Query("SELECT COUNT(column) FROM post_table")
//    fun getDataCount(): Int


}