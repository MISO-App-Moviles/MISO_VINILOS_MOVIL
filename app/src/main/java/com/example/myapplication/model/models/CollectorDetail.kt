package com.example.myapplication.model.models

import org.json.JSONArray
import org.json.JSONObject

data class CollectorDetail(
    val id: Int? = null,
    val name: String? = null,
    val telephone: String? = null,
    val email: String? = null,
    val favoritePerformers: List<PreviewPerformer> = emptyList(),
    var albums: List<PreviewAlbum>? = null
) {

    constructor(collectorDetail: JSONObject?) : this(
        collectorDetail?.optInt("id") ?: 0,
        collectorDetail?.optString("name"),
        collectorDetail?.optString("telephone"),
        collectorDetail?.optString("email"),
        parseFavoritePerformers(collectorDetail?.optJSONArray("favoritePerformers")),
    )
}

data class PreviewPerformer (
    var id           : Int?    = null,
    var name         : String? = null,
    var image        : String? = null,
)

data class PreviewAlbum (
    var id: Int,
    var name:String? = null,
    var cover: Comparable<*>? = null
)

private fun parseFavoritePerformers(performersArray: JSONArray?): List<PreviewPerformer> {
    val performersList = mutableListOf<PreviewPerformer>()
    performersArray?.let {
        for (i in 0 until it.length()) {
            val performerObject = it.optJSONObject(i)
            performerObject?.let { performer ->
                performersList.add(
                    PreviewPerformer(
                    performer.optInt("id"),
                    performer.optString("name"),
                    performer.optString("image"),
                )
                )
            }
        }
    }
    return performersList
}

public fun parseAlbums(albumsArray: JSONArray?): List<PreviewAlbum> {
    val albumsList = mutableListOf<PreviewAlbum>()
    albumsArray?.let {
        for (i in 0 until it.length()) {
            val albumObject = it.optJSONObject(i)
            albumObject?.optJSONObject("album")?.let { album ->
                albumsList.add(
                    PreviewAlbum(
                        album.optInt("id"),
                        album.optString("name"),
                        album.optString("cover")
                    )
                )
            }
        }
    }
    return albumsList
}
