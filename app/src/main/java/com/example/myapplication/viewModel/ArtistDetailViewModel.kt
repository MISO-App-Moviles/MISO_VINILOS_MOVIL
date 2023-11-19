package com.example.myapplication.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.models.ArtistDetail
import com.example.myapplication.model.models.PreviewAlbum
import com.example.myapplication.model.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistDetailViewModel(application: Application, idArtist: Int) : AndroidViewModel(application) {
    val id:Int = idArtist

    private val artistRepository = ArtistRepository(application)

    private val _artistDetail = MutableLiveData<ArtistDetail>()
    val artist: LiveData<ArtistDetail>
        get() = _artistDetail

    private val _albums = MutableLiveData<List<PreviewAlbum>>()
    val tracks: LiveData<List<PreviewAlbum>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
        refreshAlbumsFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        artistRepository.refreshDetailData(id, {
            _artistDetail.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    private fun refreshAlbumsFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO){
                    var data = artistRepository.refreshAlbumsData(id)
                    _albums.postValue(data)
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

    class Factory(val app: Application, val idArtist: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistDetailViewModel(app, idArtist) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}