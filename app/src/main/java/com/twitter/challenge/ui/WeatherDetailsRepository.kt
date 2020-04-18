package com.twitter.challenge.ui

import androidx.lifecycle.LiveData
import com.twitter.challenge.data.api.WeatherReportInterface
import com.twitter.challenge.data.repository.NetworkState
import com.twitter.challenge.data.repository.WDNetworkDataSource
import com.twitter.challenge.data.vo.WeatherReport
import io.reactivex.disposables.CompositeDisposable

class WeatherDetailsRepository(private val apiService: WeatherReportInterface) {
    private lateinit var wdNetworkDataSource: WDNetworkDataSource
    private val days = 5

    fun fetchCurrentWeatherDetails(compositeDisposable: CompositeDisposable)  :LiveData<WeatherReport>{
        wdNetworkDataSource = WDNetworkDataSource(apiService,compositeDisposable)
        wdNetworkDataSource.fetchCurrentWeather()

        return wdNetworkDataSource.weatherDetailsResponse
    }
    fun getWeatherDetailsNetworkState(): LiveData<NetworkState> {
        return wdNetworkDataSource.networkState
    }

    fun getFutureWeatherDetails(compositeDisposable: CompositeDisposable)  : LiveData<Float> {
        wdNetworkDataSource = WDNetworkDataSource(apiService,compositeDisposable)
        wdNetworkDataSource.fetchNextFiveDaysWeather(days)

        return wdNetworkDataSource.standardDeviation
    }
}