package com.capstone.monu.data.remote.model

import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("ENERC_KCAL")
    val calories : Float,

    @SerializedName("PROCNT")
    val protein : Float,

    @SerializedName("FAT")
    val fat : String,

    @SerializedName("CHOCDF")
    val carbohydrate  : Float,

    @SerializedName("FIBTG")
    val fiber : Float
)