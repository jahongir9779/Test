package com.superdispatch.task.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "post_table")
class ObjPost(
    @ColumnInfo(name = "title") var title: String = "",
    @ColumnInfo(name = "author") var author: String = "",
    @ColumnInfo(name = "publishedAt") var publishedAt: Long = DateTime.now().millis
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0
}
//data class ObjData(var properties: HashMap<String, Any>)