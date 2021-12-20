package com.capstone.monu.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.monu.ui.daily.DailyViewModel
import com.capstone.monu.ui.detail.DetailViewModel
import com.capstone.monu.ui.dialog.DialogViewModel
import com.capstone.monu.ui.food.FoodViewModel
import com.capstone.monu.ui.home.HomeViewModel
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
            modelClass.isAssignableFrom(DailyViewModel::class.java) -> DailyViewModel(monuRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(monuRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(monuRepository) as T
            modelClass.isAssignableFrom(DialogViewModel::class.java) -> DialogViewModel(monuRepository) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
