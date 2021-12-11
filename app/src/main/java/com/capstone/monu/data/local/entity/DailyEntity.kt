package com.capstone.monu.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_food")
data class DailyEntity(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id : Int = 0,

    @NonNull
    @ColumnInfo(name = "date")
    val date : String,

    @NonNull
    @ColumnInfo(name = "food")
    var food : String,

    @NonNull
    @ColumnInfo(name = "eat_time")
    var eatTime : String,

    @NonNull
    @ColumnInfo(name = "target_calories")
    val targetCalories : Float,

    @NonNull
    @ColumnInfo(name = "calories")
    var calories : Float,

    @NonNull
    @ColumnInfo(name = "target_protein")
    val targetProtein : Float,

    @NonNull
    @ColumnInfo(name = "protein")
    var protein : Float,

    @NonNull
    @ColumnInfo(name = "target_fat")
    val targetFat : Float,

    @NonNull
    @ColumnInfo(name = "fat")
    var fat : Float,

    @NonNull
    @ColumnInfo(name = "target_carbs")
    val targetCarbs : Float,

    @NonNull
    @ColumnInfo(name = "carbs")
    var carbs : Float
)