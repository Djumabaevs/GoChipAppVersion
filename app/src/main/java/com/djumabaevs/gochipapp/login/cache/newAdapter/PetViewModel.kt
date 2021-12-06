package com.djumabaevs.gochipapp.login.cache.newAdapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.djumabaevs.gochipapp.login.cache.Pet
import com.djumabaevs.gochipapp.login.cache.PetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    val repository: PetRepository
) : ViewModel() {
    fun savePets(pets: List<Pet>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savePets(pets)
        }
    }

    val pets = repository.getPets().asLiveData()
}