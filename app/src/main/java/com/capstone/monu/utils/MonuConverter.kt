package com.capstone.monu.utils

import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object MonuConverter {
    fun doubleToFloor(value : Double) : Double {
         return value.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }

    fun changeToList(str : String) = str.split("-").toList()

    fun getDates(date : String, days : Int) : List<String> {
        val list = ArrayList<String>()
        val calendar = Calendar.getInstance()
        val splitDates = date.split("-").toList()
        calendar.set(Calendar.YEAR, splitDates[0].toInt())
        calendar.set(Calendar.MONTH + 1, splitDates[1].toInt())
        calendar.set(Calendar.DAY_OF_MONTH, splitDates[2].toInt())
        val sdf = SimpleDateFormat("yyy-MM-dd", Locale.getDefault())
        for (i in 0 until days) {
            list.add(sdf.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return list
    }

}