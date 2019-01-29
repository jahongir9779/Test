package com.superdispatch.task.base

//import com.superdispatch.task.di.component.DaggerPresenterInjector
import com.superdispatch.task.di.component.DaggerPresenterInjector
import com.superdispatch.task.di.component.PresenterInjector
import com.superdispatch.task.di.module.ApplicationModule
import com.superdispatch.task.di.module.ContextModule
import com.superdispatch.task.di.module.NetworkModule
import com.superdispatch.task.ui.main.MainPresenter

/**
 * Base presenter any presenter of the application must extend. It provides initial injections and
 * required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .applicationModule(ApplicationModule(view.getCurrentContext().applicationContext as BaseApplication))
        .build()

    init {
        inject()
    }

    /**
     * This method may be called when the presenter view is created
     */
    open fun onViewCreated() {}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed() {}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MainPresenter -> injector.inject(this)
        }
    }
}