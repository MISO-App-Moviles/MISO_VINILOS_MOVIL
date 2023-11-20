package com.example.myapplication.model.repository

import android.app.Application
import com.android.volley.VolleyError
import com.example.myapplication.model.models.Collector
import com.example.myapplication.model.serviceAdapter.CollectorServiceAdapter

class CollectorRepository (private val application: Application) {

    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        CollectorServiceAdapter.getInstance(application).getCollectors({
            //Guardar los albumes de la variable it en un almacén de datos local para uso futuro
            callback(it)
        },
            onError
        )
    }
}