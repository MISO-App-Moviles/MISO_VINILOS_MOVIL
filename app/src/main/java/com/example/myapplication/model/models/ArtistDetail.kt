package com.example.myapplication.model.models

import com.example.myapplication.utils.DateUtil
import org.json.JSONArray
import org.json.JSONObject

data class ArtistDetail (
  var id              : Int?                       = null,
  var name            : String?                    = null,
  var image           : String?                    = null,
  var description     : String?                    = null,
  var birthDate       : String?                    = null,
  var albums          : List<Album>                = emptyList(),
  var performerPrizes : List<PerformerPrizes>      = emptyList()
){
  constructor(artistDetail: JSONObject?) : this(
    artistDetail?.optInt("id") ?: 0,
    artistDetail?.optString("name") ?: "",
    artistDetail?.optString("image") ?: "",
    artistDetail?.optString("description") ?: "",
    DateUtil.format(artistDetail?.optString("birthDate") ?: ""),
    getAlbums(artistDetail?.optJSONArray("albums")),
    getPerformerPrizes(artistDetail?.optJSONArray("performerPrizes")),
  )
}

private fun getPerformerPrizes(performerArray: JSONArray?): List<PerformerPrizes> {
  val performerList = mutableListOf<PerformerPrizes>()
  performerArray?.let {
    for (i in 0 until it.length()) {
      val trackObject = it.optJSONObject(i)
      trackObject?.let { performer ->
        val performerItem = PerformerPrizes(performer)
        performerList.add(performerItem)
      }
    }
  }
  return performerList
}

private fun getAlbums(albumArray: JSONArray?): List<Album> {
  val albumList = mutableListOf<Album>()
  albumArray?.let {
    for (i in 0 until it.length()) {
      val albumObject = it.optJSONObject(i)
      albumObject?.let { album ->
        val albumItem = Album(album)
        albumList.add(albumItem)
      }
    }
  }
  return albumList
}