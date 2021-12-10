package com.example.cryptoapp.data

import com.example.cryptoapp.model.Crypto
import retrofit2.http.GET

interface CryptoApi {

    @GET("assets")
    suspend fun getCryptos(): Crypto

}