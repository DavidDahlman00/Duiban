package com.example.duiban.models

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MessageClass(val idFrom: String = "",
                   val idTo: String = "",
                   val nameFrom: String = "",
                   val nameTo: String = "",
                   val message: String = "",
                   val time: Long = 0) {
    companion object {
        fun  getDateTime(time: Long):  String?{

            val sdf_old_messages = SimpleDateFormat("yyyy/MM/dd")
            val sdf_new_messages = SimpleDateFormat("HH:mm:ss")
            val calender = Calendar.getInstance()
            sdf_old_messages.timeZone = TimeZone.getTimeZone(calender.timeZone.toString())
            sdf_new_messages.timeZone = TimeZone.getTimeZone(calender.timeZone.toString())
            val date = Date(time)
            val time_now = Date(System.currentTimeMillis())
            if (sdf_old_messages.format(date) == sdf_old_messages.format(time_now)){
                return sdf_new_messages.format(date)
            }
            return sdf_old_messages.format(date)
        }
    }

    fun  getDateTime(time: Long):  String?{

        val sdf_old_messages = SimpleDateFormat("yyyy/MM/dd")
        val sdf_new_messages = SimpleDateFormat("HH:mm:ss")
        val calender = Calendar.getInstance()
        sdf_old_messages.timeZone = TimeZone.getTimeZone(calender.timeZone.toString())
        sdf_new_messages.timeZone = TimeZone.getTimeZone(calender.timeZone.toString())
        val date = Date(time)
        val time_now = Date(System.currentTimeMillis())
        if (sdf_old_messages.format(date) == sdf_old_messages.format(time_now)){
            return sdf_new_messages.format(date)
        }
        return sdf_old_messages.format(date)
    }
}