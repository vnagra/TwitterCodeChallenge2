package com.twitter.challenge.data.api

import com.twitter.challenge.data.vo.WeatherReport
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherReportInterface {
    //Endpoint for current weather
    //https://twitter-code-challenge.s3.amazonaws.com/current.json
    @GET("current.json")
    fun getCurrentWeatherDetails(): Single<WeatherReport>

    // Endpoints: future_<n>.json where n is the nth day you want to fetch data for.
    // For example, n=1 will fetch tomorrow's weather.
    //https://twitter-code-challenge.s3.amazonaws.com/future_1.json
    @GET("future_{day}.json")
    fun getFutureWeatherDetails(@Path("day") id:Int): Observable<WeatherReport>
}