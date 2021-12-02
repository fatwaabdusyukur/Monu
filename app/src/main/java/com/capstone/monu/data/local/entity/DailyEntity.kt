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
    val targetCalories : Int,

    @NonNull
    @ColumnInfo(name = "calories")
    var calories : Int
)