package com.djumabaevs.gochipapp.login.newLogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myToken: MutableLiveData<Response<TokenResponse>> = MutableLiveData()
    val mySurcharge: MutableLiveData<Response<SurchargeResponse>> = MutableLiveData()
    val myDataName: MutableLiveData<Response<List<DataResponse>>> = MutableLiveData()

    fun getJWTToken(
        username: String,
        password: String,
        grantType: String,
        clientId: String
    ) {
        viewModelScope.launch {
            val token: Response<TokenResponse> = repository.getJWTToken(username, password, grantType, clientId)
            myToken.value = token
        }
    }

    fun getSurcharge(lat: String, lon: String) {
        viewModelScope.launch {
            val surcharge: Response<SurchargeResponse> = repository.fetchSurcharge(lat, lon)
            mySurcharge.value = surcharge
        }
    }

    fun getData() {
        viewModelScope.launch {
            val dataName: Response<List<DataResponse>> = repository.getData()
            myDataName.value = dataName
        }
    }
}