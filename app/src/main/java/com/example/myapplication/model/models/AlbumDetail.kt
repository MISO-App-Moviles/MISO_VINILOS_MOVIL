package com.example.myapplication.model.models

import org.json.JSONArray
import org.json.JSONObject

data class AlbumDetail(
    val id          : Int?                = null,
    val name        : String?             = null,
    val cover       : String?             = null,
    val releaseDate : String?             = null,
    val description : String?             = null,
    val genre       : String?             = null,
    val recordLabel : String?             = null,
    val tracks      : List<Tracks>   = emptyList(),
    val performers  : List<Performers>   = emptyList(),
    val comments    : List<Comments> = emptyList()
){
    constructor(albumDetail: JSONObject?): this(
        albumDetail?.optInt("id") ?: 0,
        albumDetail?.optString("name"),
        albumDetail?.optString("cover"),
        albumDetail?.optString("releaseDate"),
        albumDetail?.optString("description"),
        albumDetail?.optString("genre"),
        albumDetail?.optString("recordLabel"),
        parseTracks(albumDetail?.optJSONArray("tracks")),
        parsePerformers(albumDetail?.optJSONArray("performers")),
        parseComments(albumDetail?.optJSONArray("comments"))
    )
}

data class Tracks (
    var id       : Int?    = null,
    var name     : String? = null,
    var duration : String? = null
)

data class Comments (
    var id          : Int?    = null,
    var description : String? = null,
    var rating      : Int?    = null
)

data class Performers (
    var id           : Int?    = null,
    var name         : String? = null,
    var image        : String? = null,
    var description  : String? = null,
    var creationDate : String? = null
)

private fun parseTracks(tracksArray: JSONArray?): List<Tracks> {
    val tracksList = mutableListOf<Tracks>()
    tracksArray?.let {
        for (i in 0 until it.length()) {
            val trackObject = it.optJSONObject(i)
            trackObject?.let { track ->
                val trackItem = Tracks(
                    track.optInt("id"),
                    track.optString("name"),
                    track.optString("duration")
                )
                tracksList.add(trackItem)
            }
        }
    }
    return tracksList
}

private fun parsePerformers(performersArray: JSONArray?): List<Performers> {
    val performersList = mutableListOf<Performers>()
    performersArray?.let {
        for (i in 0 until it.length()) {
            val performerObject = it.optJSONObject(i)
            performerObject?.let { performer ->
                val performerItem = Performers(
                    performer.optInt("id"),
                    performer.optString("name"),
                    performer.optString("image"),
                    performer.optString("description"),
                    performer.optString("creationDate")
                )
                performersList.add(performerItem)
            }
        }
    }
    return performersList
}

private fun parseComments(commentsArray: JSONArray?): List<Comments> {
    val commentsList = mutableListOf<Comments>()
    commentsArray?.let {
        for (i in 0 until it.length()) {
            val commentObject = it.optJSONObject(i)
            commentObject?.let { comment ->
                val commentItem = Comments(
                    comment.optInt("id"),
                    comment.optString("description"),
                    comment.optInt("rating")
                )
                commentsList.add(commentItem)
            }
        }
    }
    return commentsList
}