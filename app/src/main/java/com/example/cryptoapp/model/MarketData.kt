package com.example.cryptoapp.model

data class MarketData (
    val price_usd: Double,
    val percent_change_usd_last_24_hours: Double,
    val volume_last_24_hours: Double,
    val price_eth: Double,
    val price_btc: Double,
)