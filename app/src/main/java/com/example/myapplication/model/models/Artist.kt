package com.example.myapplication.model.models

import com.example.myapplication.utils.DateUtil
import org.json.JSONObject

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
){
    constructor(artist: JSONObject?) : this(
        artist?.optInt("id") ?: 0,
        artist?.optString("name") ?: "",
        artist?.optString("image") ?: "",
        artist?.optString("description") ?: "",
        DateUtil.format(artist?.optString("birthDate") ?: "")
    )
}
