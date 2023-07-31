package com.yudikryn.productapps.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yudikryn.productapps.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1, exportSchema = false
)
abstract class LocalDb : RoomDatabase() {
    abstract fun localDao(): LocalDao

    companion object {

        @Volatile
        private var INSTANCE: LocalDb? = null

        fun getInstance(context: Context): LocalDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDb::class.java,
                    "product_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
