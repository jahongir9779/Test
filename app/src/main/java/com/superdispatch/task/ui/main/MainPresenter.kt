package com.superdispatch.task.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.superdispatch.task.base.BasePresenter
import com.superdispatch.task.data.local.PostDao
import com.superdispatch.task.data.local.PostLocalDataSource
import com.superdispatch.task.models.ObjPost
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainPresenter(mainView: MainView) : BasePresenter<MainView>(mainView) {

    var postsLiveData: LiveData<PagedList<ObjPost>>? = null


    init {
//        var factory: DataSource.Factory<Int, ObjPost> =
//            db.getFreshPosts(8000)
//
//        var pagedListBuilder: LivePagedListBuilder<Int, ObjPost> = LivePagedListBuilder<Int, ObjPost>(factory, 10)
//        postsLiveData = pagedListBuilder.build()


    }

    companion object {
        const val PAGE_SIZE = 10
        const val POST_ADDING_PERIOD = 1L
    }


    override fun onViewCreated() {
        simulateError()
//        clearDb()
//        populateDb()
        startAddingPostsEachSecond()
    }

    private fun startAddingPostsEachSecond() {
        disposables += Observable
            .interval(POST_ADDING_PERIOD, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { n ->
                val posts = ArrayList<ObjPost>()
                for (i in 0..4) posts.add(ObjPost("Title ", "Author ", DateTime.now().millis))
                disposables += Completable.fromAction { db.insertAll(posts) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d("DB ACTION ", "INsERTED  POSTS " + posts.size)
                    }, {
                        Log.d("DB ACTION ERROR", "INsERT ERROR " + posts.size + it.toString())
                    })

            }.subscribe()

    }

    private fun simulateError() {
        view.showError("“Ooops, something is\n" + "wrong. Please, try again")
    }

    @Inject
    lateinit var db: PostLocalDataSource
    @Inject
    lateinit var dao: PostDao
//    @Inject
//    lateinit var data: PostRoomDatabase


    fun clearDb() {

        disposables += Completable.fromAction { db.clearTable() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("DB ACTION ", "CLEAR TABLE SUCCESS")
                view.tableCleared()
            }, {
                view.showError("There was a problem clearing table")

                Log.d("DB ACTION ERROR", "CLEAR ERROR " + it.toString())
            })
    }

    fun populateDb() {
        var posts = ArrayList<ObjPost>()
        for (i in 0..99) {
            posts.add(ObjPost("Title ", "Author ", DateTime.now().millis))
        }
        disposables += Completable.fromAction { db.insertAll(posts) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("DB ACTION ", "POPULATE TABLE SUCCESS")
            }, {
                Log.d("DB ACTION ERROR", "POPULATE TABLE ERROR " + it.toString())
            })


    }

    val disposables = CompositeDisposable()

    var lastItemIndex = 0

    fun getInitialData() {
        disposables += db.getPosts(10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ posts ->

                if (observer != null) postsLiveData?.removeObserver(observer!!)
                postsLiveData = LivePagedListBuilder(
                    db.getFreshPosts(if (posts.isNullOrEmpty()) 0 else posts[0].id),
                    PAGE_SIZE
                ).build()
                setupObserver()
                view.loadInitialData(posts)
                if (posts.isNullOrEmpty()) return@subscribe
                lastItemIndex = posts[posts.size - 1].id

                Log.d("DB ACTION ", "GET ALL " + posts.size)
            }, { throwable ->
                view.showError("")
                Log.d("DB ACTION ERROR", "GET ALL $throwable")
            })
    }

    fun getAllFreshPosts(firstVisiblePostId: Int) {

        disposables += db.getAllFreshPosts(firstVisiblePostId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ posts ->

                if (observer != null) postsLiveData?.removeObserver(observer!!)
                postsLiveData = LivePagedListBuilder(
                    db.getFreshPosts(if (posts.isNullOrEmpty()) 0 else posts[0].id),
                    PAGE_SIZE
                ).build()
                setupObserver()
                view.addMoreToTop(posts)
                if (posts.isNullOrEmpty()) return@subscribe
                lastItemIndex = posts[posts.size - 1].id
                Log.d("DB ACTION ", "GET ALL " + posts.size)
            }, { throwable ->
                view.showError("")
                Log.d("DB ACTION ERROR", "GET ALL $throwable")
            })

    }

    var observer: Observer<PagedList<ObjPost>>? = null

    fun setupObserver() {
        observer = Observer { posts ->
            view.liveDataChanged(posts)
        }
        postsLiveData?.observe(view.getCurrentContext() as MainActivity, observer!!)
    }

    override fun onViewDestroyed() {
        disposables.dispose()
    }


    fun getMore() {
        disposables += db.getPostsAfter(lastItemIndex, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ posts ->
                view.loadMoreData(posts)
                if (posts.isNullOrEmpty()) return@subscribe
                lastItemIndex = posts[posts.size - 1].id


                Log.d("DB ACTION ", "GET ALL " + posts.size)
            }, { throwable ->
                view.showError("")
                Log.d("DB ACTION ERROR", "GET ALL $throwable")
            })

    }

    operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
        this.add(disposable)
    }

    fun reAssignObserver(firstVisiblePostId: Int) {
        postsLiveData?.removeObservers(view.getCurrentContext() as MainActivity)
        postsLiveData = LivePagedListBuilder(
            db.getFreshPosts(firstVisiblePostId),
            PAGE_SIZE
        ).build()
    }

}