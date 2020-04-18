package com.twitter.challenge.util

import java.lang.Float.POSITIVE_INFINITY
import kotlin.math.pow
import kotlin.math.sqrt

object StandardDeviation {
    fun standardDeviation(dataPoints: List<Float>): Float {
        var total = 0f
        for (point in dataPoints) {
            total += point
        }
        val average = total / dataPoints.size
        var summation = 0.0
        for (point in dataPoints) {
            summation += (point - average).toDouble().pow(2.0)
        }
        if(dataPoints.size -1 == 0){
            return POSITIVE_INFINITY
        }
        return sqrt(summation / (dataPoints.size - 1)).toFloat()
    }
}