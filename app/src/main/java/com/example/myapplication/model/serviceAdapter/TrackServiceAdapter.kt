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
import com.example.myapplication.model.models.Track
import org.json.JSONArray
import org.json.JSONObject

class TrackServiceAdapter constructor(context: Context){
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    companion object{
        const val BASE_URL= "http://34.123.253.204:3000/"
        var instance: TrackServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: TrackServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    fun getTracksByAlbumId(idAlbum: Int, onComplete: (resp: List<Track>) -> Unit, onError: (error: VolleyError) -> Unit){
        requestQueue.add(getRequest("albums/$idAlbum/tracks",
            Response.Listener<String>{ response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Track>()
                for (i in 0 until resp.length()) {
                    list.add(i, Track(resp.getJSONObject(i)))
                }
                onComplete(list)
            },
            Response.ErrorListener{
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
}