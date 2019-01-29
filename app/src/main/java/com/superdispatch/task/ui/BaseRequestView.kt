package com.superdispatch.task.ui

import androidx.annotation.StringRes
import com.superdispatch.task.base.BaseView

interface BaseRequestView : BaseView {

    /**
     * Updates the previous posts by the specified ones
     * @param posts the list of posts that will replace existing ones
     */
    fun reqSuccess()

    /**
     * Displays an error in the view
     * @param error the error to display in the view
     */
    fun showError(error: String)

    /**
     * Displays an error in the view
     * @param errorResId the resource id of the error to display in the view
     */
    fun showError(@StringRes errorResId: Int) {
        this.showError(getCurrentContext().getString(errorResId))
    }

    /**
     * Displays the loading indicator of the view
     */
    fun showLoading()

    /**
     * Hides the loading indicator of the view
     */
    fun hideLoading()

}