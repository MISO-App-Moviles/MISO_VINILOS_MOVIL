package com.example.myapplication.model.models

import org.json.JSONObject

data class PerformerPrizes (
  var id             : Int?    = null,
  var premiationDate : String? = null
){
  constructor(performerPrizes: JSONObject?) : this(
    performerPrizes?.optInt("id") ?: 0,
    performerPrizes?.optString("premiationDate") ?: "",
  )
}