package com.capstone.monu.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.data.local.entity.FoodEntity

@Database(entities = [FoodEntity::class, DailyEntity::class], version = 1)
abstract class MonuDatabase : RoomDatabase() {
    abstract fun foodDao() : FoodDao

    companion object {
        private var INSTANCE : MonuDatabase? = null

        fun getDatabase(context: Context) : MonuDatabase? {
            if (INSTANCE == null) {
                synchronized(MonuDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MonuDatabase::class.java, "db_monu")
                        .build()
                }
            }
            return INSTANCE
        }
    }

}