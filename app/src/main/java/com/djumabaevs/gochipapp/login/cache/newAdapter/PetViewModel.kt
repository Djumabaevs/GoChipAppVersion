package com.djumabaevs.gochipapp.login.cache.newAdapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.djumabaevs.gochipapp.login.cache.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    repository: PetRepository
) : ViewModel() {

    val pets = repository.getPets().asLiveData()
}