package com.example.myapplication.model.models

import java.util.Date

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: Date,
)
