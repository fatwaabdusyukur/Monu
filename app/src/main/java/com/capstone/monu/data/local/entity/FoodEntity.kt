package com.capstone.monu.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class FoodEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id : String,

    @ColumnInfo(name = "label")
    val label : String,

    @ColumnInfo(name = "image")
    val image : String?,

    @ColumnInfo(name = "calories")
    val calories : Double,

    @ColumnInfo(name = "ingredientLines")
    val ingredientLines : String,

    @ColumnInfo(name = "ingredientImage")
    val ingredientImage : String,

    @ColumnInfo(name = "fat")
    val fat : Double,

    @ColumnInfo(name = "protein")
    val protein : Double,

    @ColumnInfo(name = "carbohydrate")
    val carbohydrate : Double
)