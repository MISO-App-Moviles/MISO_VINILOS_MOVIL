package com.example.myapplication.model.models

import org.json.JSONObject

data class Track (
    val trackId: Int? = null,
    val name:String,
    val duration:String
){
    constructor(track: JSONObject?) : this(
        track?.optInt("id") ?: 0,
        track?.optString("name") ?: "",
        track?.optString("duration") ?: "",
    )
}