package com.capstone.monu.ui.food

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.capstone.monu.data.local.entity.FoodEntity
import com.capstone.monu.utils.RANDOM_ING
import com.capstone.monu.utils.repository.MonuRepository
import com.capstone.monu.utils.vo.Resource

class FoodViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    private val _food = MutableLiveData<String>()

    init {
        _food.value = RANDOM_ING
    }

    fun setFood(food : String) {
        _food.value = food
    }

    val food : LiveData<Resource<PagedList<FoodEntity>>> = _food.switchMap {
        monuRepository.getFoods(it)
    }

}


