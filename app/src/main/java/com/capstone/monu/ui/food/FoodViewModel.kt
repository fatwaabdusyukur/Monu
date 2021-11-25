package com.capstone.monu.ui.food

import androidx.lifecycle.ViewModel
import com.capstone.monu.utils.repository.MonuRepository

class FoodViewModel(private val monuRepository: MonuRepository) : ViewModel() {
    fun getFoods() = monuRepository.getFoods()
}