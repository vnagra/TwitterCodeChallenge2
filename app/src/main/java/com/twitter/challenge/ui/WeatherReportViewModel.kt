package com.twitter.challenge.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.twitter.challenge.data.repository.NetworkState
import com.twitter.challenge.data.vo.WeatherReport
import io.reactivex.disposables.CompositeDisposable



class WeatherReportViewModel(
    private val weatherDetailsRepository: WeatherDetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val STANDARD_DEVIATION = "standardDeviation"
    }

    private val compositeDisposable = CompositeDisposable()

    val weatherReport: LiveData<WeatherReport> by lazy {
        weatherDetailsRepository.fetchCurrentWeatherDetails(compositeDisposable)
    }

    val standardDeviationReport: LiveData<Float> by lazy {
        weatherDetailsRepository.getFutureWeatherDetails(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        weatherDetailsRepository.getWeatherDetailsNetworkState()
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
    fun getStandardDeviation(): LiveData<Float> {
        return savedStateHandle.getLiveData(STANDARD_DEVIATION)
    }

    fun saveStandardDeviation(standardDeviation: Float) {
        savedStateHandle.set(STANDARD_DEVIATION, standardDeviation)
    }

}