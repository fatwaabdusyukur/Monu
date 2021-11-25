package com.capstone.monu.utils.network

import com.capstone.monu.data.remote.model.FoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("v2/parser")
    fun getRandomFood(@Query("app_id")  appId : String,
                      @Query("app_key") appKey : String,
                      @Query("ingr") ingredient : String
                      ) : Call<FoodResponse>
}