package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectorsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Vista de coleccionistas"
    }
    val text: LiveData<String> = _text
}