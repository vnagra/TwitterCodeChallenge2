package com.twitter.challenge

import com.twitter.challenge.util.TemperatureConverter.celsiusToFahrenheit
import org.assertj.core.api.Java6Assertions
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class TemperatureConverterTests {
    @Test
    fun testCelsiusToFahrenheitConversion() {
        val precision = Java6Assertions.within(0.01f)
        Java6Assertions.assertThat(celsiusToFahrenheit(-50f))
            .isEqualTo(-58f, precision)
        Java6Assertions.assertThat(celsiusToFahrenheit(0f))
            .isEqualTo(32f, precision)
        Java6Assertions.assertThat(celsiusToFahrenheit(10f))
            .isEqualTo(50f, precision)
        Java6Assertions.assertThat(celsiusToFahrenheit(21.11f))
            .isEqualTo(70f, precision)
        Java6Assertions.assertThat(celsiusToFahrenheit(37.78f))
            .isEqualTo(100f, precision)
        Java6Assertions.assertThat(celsiusToFahrenheit(100f))
            .isEqualTo(212f, precision)
        Java6Assertions.assertThat(celsiusToFahrenheit(1000f))
            .isEqualTo(1832f, precision)
    }
}