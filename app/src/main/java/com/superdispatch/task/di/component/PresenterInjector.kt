package com.superdispatch.task.di.component

import com.superdispatch.task.base.BaseView
import com.superdispatch.task.di.module.ApplicationModule
import com.superdispatch.task.di.module.ContextModule
import com.superdispatch.task.di.module.NetworkModule
import com.superdispatch.task.ui.main.MainPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class), (ApplicationModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified PostPresenter.
     * @param postPresenter PostPresenter in which to inject the dependencies
     */

    fun inject(presenter: MainPresenter)


    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder
        fun applicationModule(applicationModule: ApplicationModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}