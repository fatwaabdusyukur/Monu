package com.capstone.monu.ui.food

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.monu.utils.RANDOM_ING
import com.capstone.monu.utils.repository.MonuRepository

class FoodViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    private val _food = MutableLiveData<String>()
    private val _dish = MutableLiveData<String>()

    init {
        _food.value = RANDOM_ING
    }

    fun setFood(food : String) {
        _food.value = food
    }

    fun setDish(dish : String) {
        _dish.value = dish
    }

    fun getFoods() = if (_dish.value != null) _dish.switchMap { monuRepository.getFoods(_food.value!!, it) } else _food.value.let { monuRepository.getFoods(it!!) }

}


