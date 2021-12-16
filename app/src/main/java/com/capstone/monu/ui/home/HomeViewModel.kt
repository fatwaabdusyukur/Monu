package com.capstone.monu.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.monu.utils.repository.MonuRepository
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    private val date = MutableLiveData<String>()

    init {
        date.value = SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).format(
            Calendar.getInstance().time).toString()
    }

    val daily = date.switchMap { monuRepository.getDailyByDate(it) }

}