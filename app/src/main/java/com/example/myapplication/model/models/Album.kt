package com.example.myapplication.model.models

import com.example.myapplication.utils.DateUtil
import org.json.JSONObject

data class Album (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String
) {
    constructor(albumId: JSONObject?) : this(
        albumId?.optInt("id") ?: 0,
        albumId?.optString("name") ?: "",
        albumId?.optString("cover") ?: "",
        DateUtil.format(albumId?.optString("releaseDate") ?: ""),
        albumId?.optString("description") ?: "",
        albumId?.optString("genre") ?: "",
        albumId?.optString("recordLabel") ?: ""
    )
}
