package com.superdispatch.task.data.local

import androidx.paging.DataSource
import com.superdispatch.task.models.ObjPost
import io.reactivex.Observable
import io.reactivex.Single

interface PostLocalDataSource {

    fun getAll(): Single<List<ObjPost>>
    fun getFreshPosts(firstVisiblePostId: Int): DataSource.Factory<Int, ObjPost>
    fun getAllFreshPosts(firstVisiblePostId: Int): Single<List<ObjPost>>

    fun getPosts(requestedLoadSize: Int): Single<List<ObjPost>>

    fun getPostsAfter(lastVisibleItemId: Int, requestedLoadSize: Int): Single<List<ObjPost>>

    fun insert(post: ObjPost)

    fun insertAll(posts: List<ObjPost>)

    fun update(post: ObjPost)

    fun delete(post: ObjPost)

    fun clearTable()

    fun getLastItem(): ObjPost


}