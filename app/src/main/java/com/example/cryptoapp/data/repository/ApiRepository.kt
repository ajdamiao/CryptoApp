package com.example.cryptoapp.data.repository

import com.example.cryptoapp.data.CryptoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRepository {

    private val baseUrl = "https://data.messari.io/api/v1/"

    fun makeRequest(): CryptoApi {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }
}