package com.superdispatch.task.ui.viewgroups

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.superdispatch.task.R
import com.superdispatch.task.models.ObjPost
import org.jetbrains.anko.find
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

class PostViewHolder(var context: Context, view: View) : RecyclerView.ViewHolder(view) {

    var item: ObjPost? = null

    var tv_title = view.find<TextView>(R.id.tv_title)
    var tv_author = view.find<TextView>(R.id.tv_author)
    var tv_date = view.find<TextView>(R.id.tv_publishedAt)

      var dateTimeForshow = DateTimeFormat.forPattern("MMMM dd, yyyy hh:mm").withLocale(Locale.US)

    fun bind(item: ObjPost) {
        this.item = item
        tv_title.text = "${item.title}  ${item.id}"
        tv_author.text = "${item.author}  ${item.id}"
        tv_date.text = dateTimeForshow.print(DateTime(item.publishedAt))

    }

}

