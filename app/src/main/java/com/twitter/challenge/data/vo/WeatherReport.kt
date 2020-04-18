package com.twitter.challenge.data.vo

data class WeatherReport(
    val clouds: Clouds,
    val coord: Coord,
    val name: String,
    val rain: Rain,
    val weather: Weather,
    val wind: Wind
)