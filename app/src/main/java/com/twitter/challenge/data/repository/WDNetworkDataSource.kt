package com.twitter.challenge.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.twitter.challenge.data.api.WeatherReportInterface
import com.twitter.challenge.data.vo.WeatherReport
import com.twitter.challenge.util.StandardDeviation.standardDeviation
import io.reactivex.Observable.range
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WDNetworkDataSource(
    private val apiService: WeatherReportInterface,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState
    private val _downloadedWeatherDetailsResponse = MutableLiveData<WeatherReport>()
    val weatherDetailsResponse: LiveData<WeatherReport>
        get() = _downloadedWeatherDetailsResponse

    private var _standardDeviation: Float = 0.0f
    private val _std = MutableLiveData<Float>()
    val standardDeviation: MutableLiveData<Float>
        get() = _std

    init {
        fetchCurrentWeather()
    }

    fun fetchCurrentWeather() {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getCurrentWeatherDetails()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedWeatherDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("WeatherDataSource", it.message)
                        }
                    )
            )

        } catch (e: Exception) {
            Log.e("WeatherDataSource", e.message)
        }
    }

    fun fetchNextFiveDaysWeather(days: Int) {
        val dataPoints: ArrayList<Float> = ArrayList()
        try {
            val subscribe = range(1, days)
                .concatMap { day ->
                    apiService.getFutureWeatherDetails(day)
                        .map { response ->
                            dataPoints.add(response.weather.temp)
                        }
                }
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {

                    },
                    {
                        Log.e("WeatherDataSource", it.message)
                    },
                    {
                        standardDeviation.postValue(standardDeviation(dataPoints))
                    }
                )
            _std.value = _standardDeviation

        } catch (e: Exception) {
            Log.e("WeatherDataSource", e.message)
        }

    }
}