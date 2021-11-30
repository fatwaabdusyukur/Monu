package com.capstone.monu.data.remote.model

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("foodId")
    val id : String,
    @SerializedName("quantity")
    val quantity : Float,
    @SerializedName("food")
    val label : String,
    @SerializedName("image")
    val image : String
)
