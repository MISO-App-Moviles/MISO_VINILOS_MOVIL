package com.example.myapplication.utils

import com.example.myapplication.R
import java.text.SimpleDateFormat

object DateUtil {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private val outputFormat = SimpleDateFormat("yyyy-MM-dd")
    fun format(dateString: String): String {
        try {
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date)
        } catch (e: Exception) {
            return "2023-11-04"
        }
    }

    fun isValidDate(dateString: String): Boolean {
        return try {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            formatter.isLenient = false // Important to ensure strict parsing
            formatter.parse(dateString)
            true
        } catch (e: Exception) {
            false
        }
    }
}

object ImageUtil {
    fun getImage(image: String): Comparable<*> {
        val extensions = listOf("jpg","jpeg","png","gif","bmp","webp","tiff","svg","ico")
        return if (extensions.contains(image.substringAfterLast(".").lowercase())) image else R.drawable.ic_no_image
    }
    fun isValidCover(cover: String): Boolean{
        val extensions = listOf("jpg","jpeg","png","gif","bmp","webp","tiff","svg","ico")
        return (extensions.contains(cover.substringAfterLast(".").lowercase()))
    }
}

object StringUtil {
    fun isNonEmpty(input: String): Boolean{
        return input.any { it != ' ' }
    }
}





