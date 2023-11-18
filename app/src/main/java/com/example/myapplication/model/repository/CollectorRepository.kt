package com.example.myapplication.model.repository

import android.app.Application
import com.example.myapplication.model.models.Collector
import com.example.myapplication.model.models.CollectorDetail
import com.example.myapplication.model.models.PreviewAlbum
import com.example.myapplication.model.serviceAdapter.CollectorServiceAdapter

class CollectorRepository (private val application: Application) {

    suspend fun refreshData() : List<Collector> {
        return CollectorServiceAdapter.getInstance(application).getCollectors();
    }

    suspend fun refreshDetailData(idCollector: Int) : CollectorDetail {
        return CollectorServiceAdapter.getInstance(application).getCollectorDetail(idCollector)
    }

    suspend fun refreshAlbumsData(idCollector: Int) : List<PreviewAlbum> {
        return CollectorServiceAdapter.getInstance(application).getCollectorAlbums(idCollector)
    }
}