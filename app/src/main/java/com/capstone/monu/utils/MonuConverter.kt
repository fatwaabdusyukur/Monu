package com.capstone.monu.utils

import java.math.RoundingMode

object MonuConverter {
    fun doubleToFloor(value : Double) : Double {
         return value.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }

    fun changeToList(str : String) = str.split("-").toList()

}