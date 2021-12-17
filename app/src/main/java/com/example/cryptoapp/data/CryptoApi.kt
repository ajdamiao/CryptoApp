package com.example.cryptoapp.data

import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoApi {

    @GET("assets")
    suspend fun getCryptos(): Crypto

    @GET("assets/{crypto}/metrics")
    suspend fun getCryptoInfo(
        @Path("crypto") crypto: String,
    ): CryptoDetails

}