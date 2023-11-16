package com.example.myapplication.model.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.myapplication.model.models.Track
import com.example.myapplication.model.serviceAdapter.TrackServiceAdapter

class TrackRepository(val application: Application) {
    fun refreshTracksData(idAlbum: Int, callback: (List<Track>) -> Unit, onError: (VolleyError)->Unit){
        TrackServiceAdapter.getInstance(application).getTracksByAlbumId(idAlbum, {
            callback(it)
        },
            onError)
    }
}