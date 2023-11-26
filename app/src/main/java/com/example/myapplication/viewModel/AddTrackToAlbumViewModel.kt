package com.example.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.volley.ClientError
import com.example.myapplication.model.repository.AlbumRepository
import com.example.myapplication.view.AddTrackToAlbumFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AddTrackToAlbumViewModel(application: Application) : AndroidViewModel(application)  {

    private val albumsRepository = AlbumRepository(application)

    private val _trackId = MutableLiveData<Int>()

    val trackId: LiveData<Int>
        get() = _trackId

    private var _eventNetworkError = MutableLiveData<String>("")

    val eventNetworkError: LiveData<String>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun addTrackToAlbum(idAlbum: Int, body: JSONObject) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                withContext(Dispatchers.IO) {
                    val data = albumsRepository.addTrackToAlbum(idAlbum, body)
                    _trackId.postValue(data)
                }
                _eventNetworkError.postValue("")
                _isNetworkErrorShown.postValue(false)
            } catch (e: ClientError) {
                var errorMessage = JSONObject(String(e.networkResponse.data))
                _eventNetworkError.postValue(errorMessage?.optString("message"))
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTrackToAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddTrackToAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}