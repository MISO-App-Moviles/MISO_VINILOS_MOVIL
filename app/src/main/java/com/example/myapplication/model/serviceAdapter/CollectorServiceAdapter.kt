package com.example.myapplication.model.serviceAdapter

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.model.models.Collector
import com.example.myapplication.model.models.CollectorDetail
import com.example.myapplication.model.models.PreviewAlbum
import com.example.myapplication.model.models.parseAlbums
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorServiceAdapter constructor(context: Context) {

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    companion object{
        const val BASE_URL= "http://34.123.253.204:3000/"
        var instance: CollectorServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CollectorServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    list.add(i, Collector(resp.getJSONObject(i)))
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollectorDetail(idCollector: Int) = suspendCoroutine<CollectorDetail>{ cont->
        requestQueue.add(getRequest("collectors/$idCollector",
            Response.Listener<String>{ response ->
                val collectorDetail = CollectorDetail(JSONObject(response))
                cont.resume(collectorDetail)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollectorAlbums(idCollector: Int) = suspendCoroutine<List<PreviewAlbum>>{ cont->
        requestQueue.add(getRequest("collectors/$idCollector/albums",
            Response.Listener<String>{ response ->
                val albums = parseAlbums(JSONArray(response))
                cont.resume(albums)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL +path, responseListener,errorListener)
    }
}