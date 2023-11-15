package com.example.myapplication.model.serviceAdapter

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.AlbumDetail
import org.json.JSONArray
import org.json.JSONObject

class AlbumServiceAdapter constructor(context: Context){
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    companion object{
        const val BASE_URL= "http://34.123.253.204:3000/"
        var instance: AlbumServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AlbumServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError:(error: VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    list.add(i, Album(resp.getJSONObject(i)))
                }
                onComplete(list)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    fun getAlbumDetail(idAlbum: Int, onComplete: (resp: AlbumDetail) -> Unit, onError: (error: VolleyError) -> Unit){
        requestQueue.add(getRequest("albums/$idAlbum",
            Response.Listener<String>{ response ->
                val albumDetail = AlbumDetail(JSONObject(response))
                onComplete(albumDetail)
            },
            Response.ErrorListener{
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
}