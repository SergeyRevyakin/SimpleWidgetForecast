package com.serg.simplewidgetforecast

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.serg.simplewidgetforecast.ui.base.ScopeFragment
import com.serg.simplewidgetforecast.ui.current.CurrentWeatherViewModelFactory
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CurrentWeatherFragment : ScopeFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()

//        testTextView.textSize = 30f
//        testTextView.text = "TEST"

//        val apiService =
//            OpenWeatherMapApiService(ConnectivityInterceptorImpl(this.context!!))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//
//        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
//            testTextView.text = it.name
//        })
//
//        GlobalScope.launch(Dispatchers.CurrentWeatherEntry) {
//            weatherNetworkDataSource.fetchCurrentWeather(10.0, 20.0)
//        }
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it==null) return@Observer
            testTextView.text = it.currentWeatherEntry.feelsLike.toString().plus("!!!")
        })
    }

}
