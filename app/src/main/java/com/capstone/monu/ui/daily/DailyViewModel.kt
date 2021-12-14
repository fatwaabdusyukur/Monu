package com.capstone.monu.ui.daily


import androidx.lifecycle.*
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.utils.repository.MonuRepository
import java.text.SimpleDateFormat
import java.util.*

class DailyViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    private val date = MutableLiveData<String>()

    init {
        date.value = SimpleDateFormat("yyy-M-d", Locale.getDefault()).format(Calendar.getInstance().time)
    }

    fun setDate(date: String) {
        this.date.value = date
    }

    fun addDailyMeals(date: String, targetCalories : Float) {

        val data = DailyEntity(
            date = date,
            food = "",
            eatTime = "",
            targetCalories = targetCalories,
            calories = 0F,
            targetProtein = ((targetCalories * 0.15) / 4).toFloat(),
            protein = 0F,
            targetFat = ((targetCalories * 0.20) / 9).toFloat(),
            fat = 0F,
            targetCarbs = ((targetCalories * 0.65) / 4).toFloat(),
            carbs = 0F
        )

        monuRepository.addDailySchedule(data, viewModelScope)
    }

    fun getDailyByDate() = date.switchMap { monuRepository.getDailyByDate(it) }

    fun setDailyFood(foodEntity: FoodEntity, daily : DailyEntity, eatTime : String) {
        monuRepository.setDailyMeal(daily, foodEntity, eatTime, viewModelScope)
    }

    val foods = monuRepository.getFoods("Egg")

}