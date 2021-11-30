package com.capstone.monu.data.remote.model

import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("FAT")
    val fat : NutrientsDetail,

    @SerializedName("PROCNT")
    val protein : NutrientsDetail,

    @SerializedName("CHOCDF")
    val carbohydrate : NutrientsDetail
)

data class NutrientsDetail(
    @SerializedName("label")
    val label : String,

    @SerializedName("quantity")
    val quantity : Double,
)