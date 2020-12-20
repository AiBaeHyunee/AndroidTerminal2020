package com.example.terminal2020_shenaijia2018110532.ui.weather.weather

import com.example.terminal2020_shenaijia2018110532.ui.weather.weather.CityInfo
import com.example.terminal2020_shenaijia2018110532.ui.weather.weather.Data

data class Weather(
    val cityInfo: CityInfo,
    val `data`: Data,
    val date: String,
    val message: String,
    val status: Int,
    val time: String
)