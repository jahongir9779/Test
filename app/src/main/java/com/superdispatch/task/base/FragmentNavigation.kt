package com.superdispatch.task.base

import android.content.Context

interface FragmentNavigation {
    interface View : BaseView{
        fun atachPresenter(presenter: Presenter)
    }

    interface Presenter {
        fun addFragment(fragment: BaseFragment<*>)
    }


}