package com.example.myapplication.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.models.Album
import com.example.myapplication.model.models.AlbumDetail
import com.example.myapplication.model.models.Track
import com.example.myapplication.model.repository.AlbumRepository
import com.example.myapplication.model.repository.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumDetailViewModel(application: Application, albumId: Int) : AndroidViewModel(application) {

    private val albumsRepository = AlbumRepository(application)
    private val trackRepository = TrackRepository(application)

    private val _albumDetail = MutableLiveData<AlbumDetail>()

    private val _tracks = MutableLiveData<List<Track>>()

    val id:Int = albumId

    val albumDetail: LiveData<AlbumDetail>
        get() = _albumDetail

    val tracks: LiveData<List<Track>>
        get() = _tracks

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
        refreshTracksFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO){
                    var data = albumsRepository.refreshDetailData(id)
                    _albumDetail.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){ //se procesa la excepcion
            Log.d("Error", e.toString())
            _eventNetworkError.value = true
        }
    }

    fun refreshTracksFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO){
                    var data = trackRepository.refreshTracksData(id)
                    _tracks.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){ //se procesa la excepcion
            Log.d("Error", e.toString())
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}