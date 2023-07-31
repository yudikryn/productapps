package com.yudikryn.productapps.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uniqId")
    val uniqId: Int = 0,
    @ColumnInfo(name = "id")
    val id: Int
)