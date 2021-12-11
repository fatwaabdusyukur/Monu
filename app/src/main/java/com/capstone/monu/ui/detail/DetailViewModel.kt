package com.capstone.monu.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.utils.repository.MonuRepository

class DetailViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    fun getFood(id : String) = monuRepository.getFood(id)

    fun getDaily(id : Int) = monuRepository.getDailySchedule(id)

    fun addDailyMeal(food : FoodEntity, eatTime : String, date : String) {
        monuRepository.getDailyByDate(date).value.let {
            monuRepository.setDailyMeal(it!!, food, eatTime, viewModelScope)
        }
    }

}