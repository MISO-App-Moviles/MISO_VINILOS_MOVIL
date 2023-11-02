package com.example.myapplication.utils

import java.text.SimpleDateFormat

object DateUtil {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private val outputFormat = SimpleDateFormat("yyyy-MM-dd")

    fun format(dateString: String): String {
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
}