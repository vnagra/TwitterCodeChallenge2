package com.twitter.challenge.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.twitter.challenge.R
import com.twitter.challenge.data.api.WeatherReportClient
import com.twitter.challenge.data.api.WeatherReportInterface
import com.twitter.challenge.data.repository.NetworkState
import com.twitter.challenge.data.vo.WeatherReport
import com.twitter.challenge.databinding.ActivityWeatherReportBinding
import com.twitter.challenge.util.TemperatureConverter
import kotlinx.android.synthetic.main.activity_weather_report.*



class WeatherDetailsActivity : AppCompatActivity() {


    private lateinit var viewModel: WeatherReportViewModel
    private lateinit var weatherDetailsRepository: WeatherDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityWeatherReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather_report)

        val apiService : WeatherReportInterface = WeatherReportClient.getClient()
        weatherDetailsRepository = WeatherDetailsRepository(apiService)
        viewModel = getViewModel()
        binding.lifecycleOwner = this
        viewModel.weatherReport.observe(this, Observer {
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
        setupActions()
    }


    private fun setupActions() {
        button.setOnClickListener {
            viewModel.standardDeviationReport.observe( this, Observer {
                standard_deviation_tv.text = it.toString()
                viewModel.saveStandardDeviation(it)
            })
        }
         viewModel.getStandardDeviation().observe(this, Observer {
             standard_deviation_tv.text= getString(
                 R.string.standard_deviation,
                 it,
                 TemperatureConverter.celsiusToFahrenheit(it))
        })
    }

    private fun bindUI(it: WeatherReport) {
        temperature_tv.text = getString(
            R.string.temperature,
            it.weather.temp,
            TemperatureConverter.celsiusToFahrenheit(it.weather.temp))
        wind_speed_tv.text = getString(R.string.wind_speed_unit,it.wind.speed)
        cloudiness.visibility = if(it.clouds.cloudiness > 50) View.VISIBLE else View.GONE

    }
    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    private fun getViewModel(): WeatherReportViewModel {
        return ViewModelProvider(this, object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {

                @Suppress("UNCHECKED_CAST")
                return WeatherReportViewModel(weatherDetailsRepository, handle) as T
            }
        })[WeatherReportViewModel::class.java]
    }

}