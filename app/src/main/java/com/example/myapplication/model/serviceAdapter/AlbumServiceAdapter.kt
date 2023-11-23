package com.example.myapplication.model.serviceAdapter

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.AlbumDetail
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getAlbums() = suspendCoroutine<List<Album>>{cont->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    list.add(i, Album(resp.getJSONObject(i)))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getAlbumDetail(idAlbum: Int) = suspendCoroutine { cont->
        requestQueue.add(getRequest("albums/$idAlbum",
            Response.Listener<String>{ response ->
                val albumDetail = AlbumDetail(JSONObject(response))
                cont.resume(albumDetail)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }
    suspend fun postAlbum(body: JSONObject) = suspendCoroutine { cont->
        try{
            requestQueue.add(postRequest("albums",
                body,
                Response.Listener<JSONObject> { response ->
                    val idAlbum = response.getInt("id")
                    cont.resume(idAlbum)
                },
                Response.ErrorListener {
                    cont.resumeWithException(it)
                }))
        }catch (e: Exception){
            Log.e("ASA", e.message.toString())
            throw  e
        }
    }

    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ): JsonObjectRequest {
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
}