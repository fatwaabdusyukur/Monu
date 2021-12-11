package com.capstone.monu.utils


enum class DayName(val value : String) {

    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    companion object {
        fun getByTag(day : String) =
            when(day) {
                "Mon" -> MONDAY.value
                "Tue" -> TUESDAY.value
                "Wed" -> WEDNESDAY.value
                "Thu" -> THURSDAY.value
                "Fri" -> FRIDAY.value
                "Sat" -> SATURDAY.value
                else -> SUNDAY.value
            }
    }

}