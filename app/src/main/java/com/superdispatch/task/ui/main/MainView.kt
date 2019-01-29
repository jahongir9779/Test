package com.superdispatch.task.ui.main

import androidx.paging.PagedList
import com.superdispatch.task.base.BaseView
import com.superdispatch.task.models.ObjPost


interface MainView : BaseView {

    fun loadInitialData(posts: List<ObjPost>)
    fun loadMoreData(posts: List<ObjPost>)
    fun addMoreToTop(posts: List<ObjPost>)
    fun showError(errorStr: String)
    fun tableCleared()
    fun liveDataChanged(posts: PagedList<ObjPost>?)


}
