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

    @ColumnInfo(name = "nutrient")
    val nutrient : String
)