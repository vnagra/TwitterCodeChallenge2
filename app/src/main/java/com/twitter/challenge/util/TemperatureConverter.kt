package com.twitter.challenge.util

object TemperatureConverter {
    /**
     * Converts temperature in Celsius to temperature in Fahrenheit.
     *
     * @param temperatureInCelsius Temperature in Celsius to convert.
     * @return Temperature in Fahrenheit.
     */
    @JvmStatic
    fun celsiusToFahrenheit(temperatureInCelsius: Float): Float {
        return temperatureInCelsius * 1.8f + 32
    }
}