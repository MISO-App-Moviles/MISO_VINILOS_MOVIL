package com.example.myapplication.model.models

import com.example.myapplication.utils.DateUtil
import com.example.myapplication.utils.ImageUtil
import org.json.JSONObject

data class Collector (
    val collectorId: Int,
    val name:String,
    val telephone:String,
    val email:String,
){
    constructor(collectorId: JSONObject?) : this(
        collectorId?.optInt("id") ?: 0,
        collectorId?.optString("name") ?: "",
        collectorId?.optString("telephone") ?: "",
        collectorId?.optString("email") ?: "",
    )
}
