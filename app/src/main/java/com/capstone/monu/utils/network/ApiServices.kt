package com.capstone.monu.utils.network

import com.capstone.monu.data.remote.model.FoodResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("recipes/v2")
    fun getRandomFood(@Query("type") type : String,
                      @Query("q")  ingredient : String,
                      @Query("app_id") appId : String,
                      @Query("app_key") appKey : String
    ) : Call<FoodResponse>

    @GET("recipes/v2")
    fun getRandomFood(@Query("type") type : String,
                      @Query("q")  ingredient : String,
                      @Query("app_id") appId : String,
                      @Query("app_key") appKey : String,
                      @Query("dishType") dishType : String
    ) : Call<FoodResponse>
}