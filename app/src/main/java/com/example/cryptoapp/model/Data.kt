package com.example.cryptoapp.model

data class Data (
    val symbol: String,
    val name: String,
    val slug: String,
    val market_data: MarketData,
    val metrics: Metrics,
    val all_time_high: AllTimeHigh
)