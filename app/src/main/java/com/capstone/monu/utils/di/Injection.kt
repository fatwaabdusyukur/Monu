package com.capstone.monu.utils.di

import android.content.Context
import com.capstone.monu.data.local.LocalDataSource
import com.capstone.monu.data.local.room.MonuDatabase
import com.capstone.monu.data.remote.RemoteDataSource
import com.capstone.monu.utils.repository.ContextProviders
import com.capstone.monu.utils.repository.MonuRepository

object Injection {
    fun provideRepository(context: Context) : MonuRepository {
        val database = MonuDatabase.getDatabase(context)

        val localDataSource = LocalDataSource.getInstance(database!!.foodDao())
        val ctx = ContextProviders.getInstance()

        val remoteDataSource = RemoteDataSource.getInstance()
        return MonuRepository.getInstance(remoteDataSource, localDataSource, ctx)
    }
}