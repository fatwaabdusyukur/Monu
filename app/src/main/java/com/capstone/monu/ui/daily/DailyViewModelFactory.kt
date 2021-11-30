package com.capstone.monu.ui.daily

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.monu.utils.di.Injection
import com.capstone.monu.utils.repository.MonuRepository

class DailyViewModelFactory private constructor(private val monuRepository: MonuRepository, private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        fun createFactory(context: Context) : DailyViewModelFactory =
            DailyViewModelFactory(Injection.provideRepository(context), context)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DailyViewModel::class.java) -> DailyViewModel(monuRepository, context) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}