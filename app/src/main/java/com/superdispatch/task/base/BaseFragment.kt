package com.superdispatch.task.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


abstract class BaseFragment<P : BasePresenter<*>> : Fragment(), FragmentNavigation.View {

    // the root view
    protected lateinit var rootView: View
    protected lateinit var presenter: P

    /**
     * navigation presenter instance
     * declared in base for easier access
     */

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayout(), container, false)
        presenter = instantiatePresenter()
        return rootView
    }

    protected abstract fun getLayout(): Int

    protected lateinit var navigationPresenter: FragmentNavigation.Presenter
    override fun atachPresenter(presenter: FragmentNavigation.Presenter) {
        navigationPresenter = presenter

    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P


    override fun getCurrentContext(): Context {
        return context!!
    }
}