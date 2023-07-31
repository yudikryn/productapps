package com.yudikryn.productapps.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yudikryn.productapps.data.local.entity.FavoriteEntity

@Dao
interface LocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteProduct(favoriteEntity: FavoriteEntity): Long

    @Query("Delete FROM favorite_table where id LIKE :id")
    fun unfavoriteProduct(id: Int): Int

    @Query("select * from favorite_table")
    fun getFavoriteProduct(): List<FavoriteEntity>
}