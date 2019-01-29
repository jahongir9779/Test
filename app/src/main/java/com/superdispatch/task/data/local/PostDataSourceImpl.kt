package com.superdispatch.task.data.local

import androidx.paging.DataSource
import com.superdispatch.task.base.BaseApplication
import com.superdispatch.task.models.ObjPost
import io.reactivex.Single
import javax.inject.Inject


class PostDataSourceImpl : PostLocalDataSource {
    override fun getAllFreshPosts(firstVisiblePostId: Int): Single<List<ObjPost>> {
        return database.postDao().getAllFreshPosts(firstVisiblePostId)
    }


    override fun getPostsAfter(lastVisibleItemId: Int, requestedLoadSize: Int): Single<List<ObjPost>> {
        return database.postDao().getPostsAfter(lastVisibleItemId, requestedLoadSize)

    }

    override fun getFreshPosts(firstVisiblePostId: Int): DataSource.Factory<Int, ObjPost> {
        return database.postDao().getFreshPosts(firstVisiblePostId)
    }


    init {
        BaseApplication.component.inject(this)
    }


    @Inject
    lateinit var database: PostRoomDatabase


    override fun getPosts(requestedLoadSize: Int): Single<List<ObjPost>> =
        database.postDao().getPosts(requestedLoadSize)

    override fun getAll(): Single<List<ObjPost>> {
        return database.postDao().getAll()
    }

    override fun insert(post: ObjPost) {
        database.postDao().insert(post)
    }

    override fun insertAll(posts: List<ObjPost>) {
        database.postDao().insertAll(posts)
    }

    override fun update(post: ObjPost) {
        database.postDao().update(post)
    }

    override fun delete(post: ObjPost) {
        database.postDao().delete(post)
    }


    override fun clearTable() {
        database.postDao().clearTable()
    }


    override fun getLastItem(): ObjPost {
        return database.postDao().getLastItem()
    }

}