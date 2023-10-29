package com.example.myapplication.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlbumListViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Vista de albumes"
    }
    val text: LiveData<String> = _text
}