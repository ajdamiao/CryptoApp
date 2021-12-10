package com.example.cryptoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.cryptoapp.data.repository.ApiRepository
import com.example.cryptoapp.model.Crypto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel {
    private val apiRepository = ApiRepository().makeRequest()
    private val cryptoResponse: MutableLiveData<Crypto> = MutableLiveData()

    fun getCryptoResponse() : MutableLiveData<Crypto> {
        return cryptoResponse
    }

    fun getCryptos(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cryptos = apiRepository.getCryptos()

                cryptoResponse.postValue(cryptos)
            } catch (e: Exception) {
                println("ERROR: $e")
            }
        }
    }
}