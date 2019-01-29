package com.superdispatch.task.ui.main

import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.florent37.kotlin.pleaseanimate.please
import com.superdispatch.task.R
import com.superdispatch.task.base.BaseActivity
import com.superdispatch.task.models.ObjPost
import com.superdispatch.task.ui.main.adapters.PostResyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainPresenter>(), MainView {


    override fun tableCleared() {
        hideBtnNewPosts()
        adapter?.items = mutableListOf()
        adapter?.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }


    var adapter: PostResyclerAdapter? = null

    var observer: Observer<PagedList<ObjPost>>? = null
    var postsToBeAdded = ArrayList<ObjPost>()

    override fun loadInitialData(posts: List<ObjPost>) {

        adapter = PostResyclerAdapter(this, recyclerView, posts.toMutableList())
        adapter!!.setOnLoadMoreListener(object : PostResyclerAdapter.IOnLoadMore {
            override fun onLoadMore() {
                if (!adapter!!.isLoading) {
                    presenter.getMore()
                    adapter?.addLoadingItem()
                }
            }
        })

        recyclerView.adapter = adapter

        swipeRefreshLayout.isRefreshing = false

    }


    override fun liveDataChanged(posts: PagedList<ObjPost>?) {
        if (!isBtnNewPostsShown && layoutError.visibility == View.GONE && posts!!.size > 0) {
            showBtnNewPosts()
            btn_new_posts.text = "${posts?.size} new posts"
        } else {
            btn_new_posts.text = "${posts?.size} new posts"
        }
    }

    override fun loadMoreData(posts: List<ObjPost>) {
        adapter?.removeLoadingItem()
        adapter?.items?.addAll(posts)
        adapter?.notifyItemRangeInserted(adapter!!.itemCount - posts.size, posts.size)
        swipeRefreshLayout.isRefreshing = false
    }

    override fun instantiatePresenter(): MainPresenter {
        return MainPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        presenter.onViewCreated()


        tv_clear.setOnClickListener {
            swipeRefreshLayout.isRefreshing = true
            presenter.clearDb()
        }

        btn_reload.setOnClickListener {
            layoutError.visibility = View.GONE
            swipeRefreshLayout.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = true
            presenter.getInitialData()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        swipeRefreshLayout.setOnRefreshListener {
            hideBtnNewPosts()
            adapter?.items?.clear()
            adapter?.notifyItemRangeRemoved(0, adapter!!.items.size)
            presenter.getInitialData()
        }

        please {
            animate(btn_new_posts) {
                aboveOf(cl_parent, margin = btn_new_posts.height.toFloat())
            }
        }.now()

        btn_new_posts.setOnClickListener {
            presenter.getAllFreshPosts(if (adapter!!.items.isNullOrEmpty()) 0 else adapter!!.items[0]!!.id)
            hideBtnNewPosts()
        }

    }

    override fun addMoreToTop(posts: List<ObjPost>) {
        adapter?.items?.addAll(0, posts)
        adapter?.notifyItemRangeInserted(0, posts.size)
//            postsToBeAdded.clear()
        recyclerView.scrollToPosition(0)
    }


    override fun showError(errorStr: String) {
        btn_new_posts.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
        swipeRefreshLayout.visibility = View.GONE
        tv_error.text = errorStr
        swipeRefreshLayout.isRefreshing = false

    }

    fun hideBtnNewPosts() {
        please(300) {
            animate(btn_new_posts) {
                aboveOf(cl_parent, margin = btn_new_posts.height.toFloat())
            }
        }.setInterpolator(AnticipateOvershootInterpolator()).start()
        isBtnNewPostsShown = false
    }

    var isBtnNewPostsShown = false

    fun showBtnNewPosts() {
        btn_new_posts.visibility = View.VISIBLE

        please(300) {
            animate(btn_new_posts) {
                topOfItsParent()
            }
        }.setInterpolator(AnticipateOvershootInterpolator()).start()
        isBtnNewPostsShown = true
    }

}
