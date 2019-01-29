package com.superdispatch.task.ui.main.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class RVAdrMutableNullable<T, V : RecyclerView.ViewHolder>(private val items: MutableList<T?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }


}