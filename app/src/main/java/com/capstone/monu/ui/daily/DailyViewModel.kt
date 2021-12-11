package com.capstone.monu.ui.daily

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.monu.data.local.entity.DailyEntity
import com.capstone.monu.utils.PREF_DAILY_KEY
import com.capstone.monu.utils.PREF_DATE_KEY
import com.capstone.monu.utils.repository.MonuRepository
import java.text.SimpleDateFormat
import java.util.*

class DailyViewModel(private val monuRepository: MonuRepository, context: Context) : ViewModel() {

    private val pref = context.getSharedPreferences(PREF_DAILY_KEY, Context.MODE_PRIVATE)
    private val _isAvailable = MutableLiveData<Boolean>()

    fun isAvailable() : LiveData<Boolean> {
        val prefDate = pref.getString(PREF_DATE_KEY, "")
        if ("" == prefDate) {
            _isAvailable.value = true
        } else {
            val now = SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault()).format(
                Calendar.getInstance().time).toString()
            _isAvailable.value = !prefDate.equals(now)
        }
        return _isAvailable
    }


    fun addDailyMeals(targetCalories : Float) {

        val currentDate = SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault()).format(Calendar.getInstance().time)

        val data = DailyEntity(
            date = currentDate.toString(),
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

    fun getAllDailySchedule() = monuRepository.getAllDailySchedule()

}