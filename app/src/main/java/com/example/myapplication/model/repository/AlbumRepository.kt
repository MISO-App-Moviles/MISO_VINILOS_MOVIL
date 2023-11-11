package com.example.myapplication.model.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.AlbumDetail
import com.example.myapplication.model.serviceAdapter.AlbumServiceAdapter

class AlbumRepository(val application: Application) {

    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        AlbumServiceAdapter.getInstance(application).getAlbums({
            //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }

    fun refreshDetailData(idAlbum: Int, callback: (AlbumDetail) -> Unit, onError: (VolleyError)->Unit){
        AlbumServiceAdapter.getInstance(application).getAlbumDetail(idAlbum, {
            callback(it)
        },
            onError)
    }
}