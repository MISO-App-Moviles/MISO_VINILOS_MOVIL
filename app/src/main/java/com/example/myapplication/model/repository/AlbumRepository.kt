package com.example.myapplication.model.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.AlbumDetail
import com.example.myapplication.model.serviceAdapter.AlbumServiceAdapter
import org.json.JSONObject

class AlbumRepository(val application: Application) {

    suspend fun refreshData() : List<Album> {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return AlbumServiceAdapter.getInstance(application).getAlbums();
    }

    suspend fun refreshDetailData(idAlbum: Int) : AlbumDetail {
        return AlbumServiceAdapter.getInstance(application).getAlbumDetail(idAlbum)
    }

    suspend fun postAlbum(body: JSONObject): Int {
        try{
            return AlbumServiceAdapter.getInstance(application).postAlbum(body)
        }catch (e: Exception){
            throw  e
        }
    }
}