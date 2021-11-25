package com.capstone.monu.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.monu.ui.food.FoodViewModel
import com.capstone.monu.utils.di.Injection
import com.capstone.monu.utils.repository.MonuRepository

class ViewModelFactory private constructor(private val monuRepository: MonuRepository) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FoodViewModel::class.java) -> FoodViewModel(monuRepository) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
