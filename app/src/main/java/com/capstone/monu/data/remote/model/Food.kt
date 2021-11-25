package com.capstone.monu.data.remote.model

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("hints")
    val hints : List<FoodList>
)

data class FoodList(
    @SerializedName("food")
    val food : Food
)

data class Food(
    @SerializedName("foodId")
    val id : String,
    @SerializedName("label")
    val label : String,
    @SerializedName("image")
    val image : String,
    @SerializedName("nutrients")
    val nutrients : Nutrients
)