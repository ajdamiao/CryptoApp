package com.example.cryptoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.cryptoapp.data.repository.ApiRepository
import com.example.cryptoapp.model.CryptoDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CryptoDetailsViewModel {
    private val apiRepository = ApiRepository().makeRequest()
    private val apiResponse : MutableLiveData<CryptoDetails> = MutableLiveData()

    fun getApiResponse(): MutableLiveData<CryptoDetails> {
        return apiResponse
    }

    fun getCryptoDetails(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = apiRepository.getCryptoInfo(name)

                apiResponse.postValue(request)

            }catch (exception: Exception) {
                println(exception)
            }
        }
    }
}