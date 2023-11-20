package com.example.myapplication.model.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.AlbumDetail
import com.example.myapplication.model.serviceAdapter.AlbumServiceAdapter

class AlbumRepository(val application: Application) {

    suspend fun refreshData() : List<Album> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return AlbumServiceAdapter.getInstance(application).getAlbums();
    }
}