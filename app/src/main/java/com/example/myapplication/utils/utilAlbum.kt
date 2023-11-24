package com.example.myapplication.utils

object AlbumUtility {
    fun isValidDiscography(value: String): Boolean {
        val validDiscographies = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
        return validDiscographies.contains(value.trim())
    }

    fun isValidGenre(value: String): Boolean {
        val validGenres = listOf("Classical","Salsa","Rock","Folk")
        return validGenres.contains(value.trim())
    }Sa
}