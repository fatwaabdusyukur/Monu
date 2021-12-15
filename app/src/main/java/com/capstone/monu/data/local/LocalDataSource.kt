package com.capstone.monu.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.data.local.room.FoodDao

class LocalDataSource(private val foodDao: FoodDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(foodDao: FoodDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(foodDao)
    }

    fun obtainFoods(keyword : String) : DataSource.Factory<Int, FoodEntity> = foodDao.getAllDataFood(keyword)

    fun obtainFood(id : String) : LiveData<FoodEntity> = foodDao.getDataFood(id)

    fun obtainDailyFoods(foods : List<String>) : LiveData<List<FoodEntity>> = foodDao.getDataDailyFood(foods)

    fun obtainDaily() : DataSource.Factory<Int, DailyEntity> = foodDao.getAllDailyFood()

    fun obtainDailyById(id : Int) : LiveData<DailyEntity> = foodDao.getDailyFood(id)

    fun obtainDailyByDate(date : String) : LiveData<DailyEntity> = foodDao.getDataDailyByDate(date)

    fun addFoods(foods : FoodEntity) = foodDao.insertFood(foods)

    suspend fun addDailyFood(daily : DailyEntity) = foodDao.insertDailyFood(daily)

    suspend fun updateDailyFood(daily: DailyEntity) = foodDao.updateDailyFood(daily)
}