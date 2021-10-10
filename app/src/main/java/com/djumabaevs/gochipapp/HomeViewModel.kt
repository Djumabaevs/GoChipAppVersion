package com.djumabaevs.gochipapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home"
    }
    val text: LiveData<String> = _text
}