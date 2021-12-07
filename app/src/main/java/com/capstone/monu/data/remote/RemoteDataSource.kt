package com.capstone.monu.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.monu.data.remote.model.FoodList
import com.capstone.monu.data.remote.model.FoodResponse
import com.capstone.monu.utils.API_ID
import com.capstone.monu.utils.API_KEY
import com.capstone.monu.utils.RANDOM_ING
import com.capstone.monu.utils.TYPE_REQUEST
import com.capstone.monu.utils.network.ApiConfig
import com.capstone.monu.utils.repository.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance : RemoteDataSource? = null

        private const val TAG = "RemoteDataSource"

        fun getInstance() : RemoteDataSource = instance ?: synchronized(this) { instance ?: RemoteDataSource() }
    }

    fun getFoods(ingredient : String, dish : String? = null) : LiveData<ApiResponse<List<FoodList>>> {
        val client = if (dish != null) ApiConfig.getApiServices().getRandomFood(TYPE_REQUEST, ingredient, API_ID, API_KEY, dish) else ApiConfig.getApiServices().getRandomFood(TYPE_REQUEST, ingredient, API_ID, API_KEY)
        val foods = MutableLiveData<ApiResponse<List<FoodList>>>()
        client.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                foods.value = ApiResponse.success( response.body()?.hints as List<FoodList> )
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                Log.e(TAG, "OnFailure ${t.message.toString()}")
            }
        })
        return foods
    }

}