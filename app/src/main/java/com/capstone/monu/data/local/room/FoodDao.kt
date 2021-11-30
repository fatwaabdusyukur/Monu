package com.capstone.monu.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.data.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM food")
    fun getAllDataFood() : DataSource.Factory<Int, FoodEntity>

    @Query("SELECT * FROM food WHERE id = :id")
    fun getDataFood(id : String) : LiveData<FoodEntity>

    @Query("SELECT * FROM food WHERE id IN (:foods)")
    fun getDataDailyFood(foods : List<String>) : DataSource.Factory<Int, FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(vararg food : FoodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyFood(dailyFood : DailyEntity)

    @Query("UPDATE daily_food SET food = food || '-' || :foodID WHERE id = :dailyID")
    suspend fun updateDailyFood(foodID : String, dailyID : Int)

    @Query("SELECT * FROM daily_food")
    fun getAllDailyFood() : DataSource.Factory<Int, DailyEntity>

    @Query("SELECT * FROM daily_food WHERE id = :id")
    fun getDailyFood(id : Int) : LiveData<DailyEntity>
}