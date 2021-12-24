package com.capstone.monu.ui.dialog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.monu.utils.RANDOM_ING
import com.capstone.monu.utils.repository.MonuRepository

class DialogViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    private val food = MutableLiveData<String>()

    init {
        food.value = RANDOM_ING
    }

    fun setFood(food : String) {
        this.food.value = food
    }

    val foods = food.switchMap { monuRepository.getFoods(it) }

}