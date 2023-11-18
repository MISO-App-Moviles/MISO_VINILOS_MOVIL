package com.example.myapplication.model.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.myapplication.model.models.Track
import com.example.myapplication.model.serviceAdapter.TrackServiceAdapter

class TrackRepository(val application: Application) {
    suspend fun refreshTracksData(idAlbum: Int) : List<Track> {
        return TrackServiceAdapter.getInstance(application).getTracksByAlbumId(idAlbum)
    }
}