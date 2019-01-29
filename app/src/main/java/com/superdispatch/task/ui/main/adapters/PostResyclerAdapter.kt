package com.superdispatch.task.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.superdispatch.task.R
import com.superdispatch.task.models.ObjPost
import com.superdispatch.task.ui.viewgroups.PostViewHolder
import kotlin.properties.Delegates


/**
 * Created by MJay on 18-Jan-18.
 */

class PostResyclerAdapter(var context: Context, var mRecyclerView: RecyclerView, items: MutableList<ObjPost?>) :
    RVAdrMutableNullable<ObjPost?, RecyclerView.ViewHolder>(items), AutoUpdatableAdapter {

    var isLoading: Boolean = false

    internal var visibleThreshold = 1
    internal var lastVisibleItem: Int = 0
    internal var totalItemCount: Int = 0
    private var mIOnLoadMore: IOnLoadMore? = null
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    var items: MutableList<ObjPost?> by Delegates.observable(arrayListOf()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }


    init {
        val linearLayoutManager = mRecyclerView.layoutManager as LinearLayoutManager
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (mIOnLoadMore != null) {
                        mIOnLoadMore!!.onLoadMore()
                    }
                    isLoading = true
                } else if (totalItemCount - 1 > lastVisibleItem) {
                    isLoading = false
                }
            }
        })
    }

    fun setOnLoadMoreListener(mIOnLoadMore: IOnLoadMore) {
        this.mIOnLoadMore = mIOnLoadMore
    }

    fun removeListener() {
        this.mIOnLoadMore = null
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_LOADING
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            PostViewHolder(context, LayoutInflater.from(parent.context).inflate(R.layout.li_post, parent, false))
        } else {
            LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.progress_bar_loading, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder) {
            holder.bind(items[position]!! as ObjPost)
        } else if (holder is LoadingViewHolder) {
            holder.progressBar1.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items.size
    }

    fun removeLoadingItem() {
        isLoading = false
        if (items.size == 0) return

        val newList = ArrayList(items.toMutableList())
        newList.removeAt(items.size - 1)
        items = newList
//        items.removeAt(items.size - 1)
//        notifyItemRemoved(items.size - 1)
//        notifyDataSetChanged()

    }

    fun addLoadingItem() {
        isLoading = true

        val newList = ArrayList(items.toMutableList())
        newList.add(null)
        items = newList
//        items.add(null)

//        mRecyclerView.post { notifyItemInserted(items.size - 1) }
//        notifyItemInserted(items.size - 1)
//        notifyDataSetChanged()
    }


    interface IOnLoadMore {
        fun onLoadMore()
    }


    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar1: ProgressBar = itemView.findViewById<View>(R.id.progressBar1) as ProgressBar
    }
}
