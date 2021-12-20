package com.capstone.monu.ui.daily


import androidx.lifecycle.*
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

    fun getDailyByDate() = date.switchMap { monuRepository.getDailyByDate(it) }

    fun getDailyMeals(list : List<String>) = monuRepository.getDailyMeals(list)

}