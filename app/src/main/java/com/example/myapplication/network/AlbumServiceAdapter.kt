package com.example.myapplication.network

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.models.Album
import org.json.JSONArray

class AlbumServiceAdapter constructor(context: Context){
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    companion object{
        const val BASE_URL= "https://vynils-back-heroku.herokuapp.com/"
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
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                onComplete(list)
            },

            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }


}