package com.capstone.monu.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.utils.repository.MonuRepository

class DetailViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    fun getFood(id : String) = monuRepository.getFood(id)

    fun addDailyMeal(food : String, eatTime : String, date : String, calories : Int) {
        val daily = monuRepository.getDailyByDate(date).value
        daily?.let { monuRepository.setDailyMeal(it, food, eatTime, calories, viewModelScope) }
    }

}