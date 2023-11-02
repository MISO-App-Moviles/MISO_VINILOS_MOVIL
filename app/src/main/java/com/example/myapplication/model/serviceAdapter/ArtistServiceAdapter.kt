package com.example.myapplication.model.serviceAdapter

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.model.models.Artist
import org.json.JSONArray

class ArtistServiceAdapter constructor(context: Context) {
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    //Obtiene una instancia de este mismo service adapeter
    companion object{
        const val BASE_URL= "https://vynils-back-heroku.herokuapp.com/"
        var instance: ArtistServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ArtistServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    /**
     * Obtiene los artistas
     */
    fun getArtists(onComplete:(resp:List<Artist>)->Unit, onError:(error: VolleyError)->Unit){
        requestQueue.add(
            getRequest(
                "artists",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Artist>()
                    for (i in 0 until resp.length()) {
                        list.add(i, Artist(resp.getJSONObject(i)))
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                }
            )
        )
    }

    /**
     * Realiza el llamado rest
     */
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, AlbumServiceAdapter.BASE_URL +path, responseListener,errorListener)
    }

}