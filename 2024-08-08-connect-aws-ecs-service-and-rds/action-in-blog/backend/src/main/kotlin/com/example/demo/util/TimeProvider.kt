package com.example.demo.util

import java.text.SimpleDateFormat
import java.util.*

object TimeProvider {

    @JvmStatic
    private val FORMAT: String = "yyyy-MM-dd HH:mm:ss"

    @JvmStatic
    fun now(): Long {
        return System.currentTimeMillis()
    }

    @JvmStatic
    fun toStringFormat(timestamp: Long): String {
        return SimpleDateFormat(FORMAT).format(Date(timestamp))
    }

    @JvmStatic
    fun parseStringToTimestamp(stringDate: String): Long {
        return SimpleDateFormat(FORMAT).parse(stringDate).time
    }
}