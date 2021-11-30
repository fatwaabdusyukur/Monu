package com.capstone.monu.data.remote.model

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("hits")
    val hints : List<FoodList>
)

data class FoodList(
    @SerializedName("recipe")
    val food : Food
)

data class Food(
    @SerializedName("uri")
    val id : String,
    @SerializedName("label")
    val label : String,
    @SerializedName("image")
    val image : String,
    @SerializedName("calories")
    val calories : Double,
    @SerializedName("ingredientLines")
    val ingredientLines : List<String>,
    @SerializedName("ingredients")
    val ingredient : List<Ingredient>,
    @SerializedName("totalNutrients")
    val nutrients : Nutrients
)