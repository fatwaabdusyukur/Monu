package com.capstone.monu.ui.detail

import androidx.lifecycle.ViewModel
import com.capstone.monu.utils.repository.MonuRepository

class DetailViewModel(private val monuRepository: MonuRepository) : ViewModel() {

    fun getFood(id : String) = monuRepository.getFood(id)

}