package com.capstone.monu.ui.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.utils.MonuConverter
import com.capstone.monu.utils.RANDOM_ING
import com.capstone.monu.utils.repository.MonuRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DialogViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    private val food = MutableLiveData<String>()

    init {
        food.value = RANDOM_ING
    }

    fun setFood(food : String) {
        this.food.value = food
    }

    fun setDailyFood(foodEntity: FoodEntity, daily : DailyEntity, eatTime : String) {
        monuRepository.setDailyMeal(daily, foodEntity, eatTime, viewModelScope)
    }

    val foods = food.switchMap { monuRepository.getFoods(it) }

    fun addDaily(longDay : Int, weight : Int, height : Int, age : Int, Gender : String, activity : String) {
        val bmr = if (Gender == MALE) 66.5 + (13.7 * weight) + (5 * height) - (6.8 * age) else 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age)
        val targetCalories = when(activity) {
            LIGHT -> bmr * 1.2
            MODERATE -> bmr * 1.3
            else -> bmr * 1.4
        }

        val date = SimpleDateFormat("yyy-M-d", Locale.getDefault()).format(Calendar.getInstance().time)
        val dates = MonuConverter.getDates(longDay)

        if (longDay == 1) {
            val daily = DailyEntity(
                date = date,
                food = "",
                eatTime = "",
                targetCalories = targetCalories.toFloat(),
                calories = 0F,
                targetProtein = ((targetCalories * 0.15) / 4).toFloat(),
                protein = 0F,
                targetFat = ((targetCalories * 0.20) / 9).toFloat(),
                fat = 0F,
                targetCarbs = ((targetCalories * 0.65) / 4).toFloat(),
                carbs = 0F
            )
            monuRepository.addDailySchedule(dailyEntity = daily, viewModelScope)
        } else {
            val list = ArrayList<DailyEntity>()
            for (i in 0 until longDay) {
                list.add(
                    DailyEntity(
                        date = dates[i],
                        food = "",
                        eatTime = "",
                        targetCalories = targetCalories.toFloat(),
                        calories = 0F,
                        targetProtein = ((targetCalories * 0.15) / 4).toFloat(),
                        protein = 0F,
                        targetFat = ((targetCalories * 0.20) / 9).toFloat(),
                        fat = 0F,
                        targetCarbs = ((targetCalories * 0.65) / 4).toFloat(),
                        carbs = 0F
                    )
                )
            }
            monuRepository.addDailySchedule(list = list, viewModelScope)
        }

    }

    companion object {
        private const val MALE = "Male"
        private const val LIGHT = "Lightly Activity"
        private const val MODERATE = "Moderate Activity"
    }

}