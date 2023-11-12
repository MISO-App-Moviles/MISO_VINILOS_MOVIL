package com.example.myapplication.model.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.myapplication.model.models.Artist
import com.example.myapplication.model.models.ArtistDetail
import com.example.myapplication.model.serviceAdapter.ArtistServiceAdapter

class ArtistRepository (val application: Application) {
    fun refreshData(callback: (List<Artist>) -> Unit, onError: (VolleyError) -> Unit) {
        ArtistServiceAdapter.getInstance(application).getArtists({ callback(it) }, { onError })
    }

    fun refreshDetailData(artistId: Int, callback: (ArtistDetail)->Unit, onError: (VolleyError)->Unit) {
        ArtistServiceAdapter.getInstance(application).getArtistDetail(artistId, {callback(it)},{onError})
    }
}