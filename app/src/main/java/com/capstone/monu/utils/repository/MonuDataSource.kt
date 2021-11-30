package com.capstone.monu.utils.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.utils.vo.Resource
import kotlinx.coroutines.CoroutineScope

interface MonuDataSource {

    fun getFoods() : LiveData<Resource<PagedList<FoodEntity>>>

    fun addDailySchedule(dailyEntity: DailyEntity, scope : CoroutineScope)

    fun getAllDailySchedule() : LiveData<PagedList<DailyEntity>>

    fun getDailySchedule(id : Int) : LiveData<DailyEntity>

}