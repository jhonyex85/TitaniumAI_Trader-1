package com.titanium.ai.trader

data class SignalResponse(val symbol: String, val timestamp: String, val signal: String, val price: Double, val reason: String)
data class SignalHistoryItem(val ts: String, val signal: String, val reason: String, val price: Double)
