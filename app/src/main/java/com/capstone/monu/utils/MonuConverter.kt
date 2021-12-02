package com.capstone.monu.utils

import java.lang.StringBuilder
import java.math.RoundingMode

object MonuConverter {
    fun toLines(str : String) : String {
        val arr = str.split("-").toTypedArray()
        val result = StringBuilder().append("")
        arr.forEach {
            result.append(it).append("\n")
        }
        return result.removeSuffix("\n").toString()
    }

    fun doubleToFloor(value : Double) : Double {
         return value.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }

    fun toList(str : String) : List<String> {
        return str.split(",").toList()
    }
}