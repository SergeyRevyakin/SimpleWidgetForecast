package com.serg.simplewidgetforecast.ui.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.serg.simplewidgetforecast.R
import com.serg.simplewidgetforecast.data.db.CurrentWeatherResponse
import com.serg.simplewidgetforecast.internal.GlideApp
import com.serg.simplewidgetforecast.internal.showWindDirection
import com.serg.simplewidgetforecast.ui.base.ScopeFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


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

        group_main.visibility = View.GONE
        group_loading.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this, viewModelFactory)  //redone
            .get(CurrentWeatherViewModel::class.java)

        updateToolbar()
        bindUI()


    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {

            updateLocationAndIcon(it)
            updateTemp(it)
            updateWind(it)
            updateHumidity(it)
            updatePressure(it)

            updateToolbarSubtitle(it)

            group_loading.visibility = View.GONE
            group_main.visibility = View.VISIBLE


        })
    }

    private fun updateLocationAndIcon(response: CurrentWeatherResponse?) {
        textView_location.text = response?.name
        textView_descriprion.text = response?.weather?.get(0)?.description

        GlideApp.with(this@CurrentWeatherFragment)
            .load("https://openweathermap.org/img/wn/${response?.weather?.get(0)?.icon}@2x.png")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView_condition_icon)
    }

    private fun updateTemp(response: CurrentWeatherResponse?) { //feelsLike: String, tempMin: String, tempMax: String

        textView_feelslike.text = String.format("%.1f", response?.main?.feelsLike)
        if (response != null && response.main.feelsLike > 0)
            textView_feelslike.setTextColor(resources.getColor(R.color.secondaryColor))
        else textView_feelslike.setTextColor(resources.getColor(R.color.primaryDarkColor))

        if (response?.main?.tempMin != response?.main?.tempMax) {
            textView_temperature.text = getString(R.string.temperature)
                .plus(" ")
                .plus(String.format("%.1f", response?.main?.tempMin))
                .plus(" .. ")
                .plus(String.format("%.1f", response?.main?.tempMax))
        } else textView_temperature.text = getString(R.string.temperature)
            .plus(" ")
            .plus(String.format("%.1f", response?.main?.tempMin))
    }

    private fun updateWind(response: CurrentWeatherResponse?) {
        textView_wind.text = getString(R.string.wind)
            .plus(" ${String.format("%.1f", response?.wind?.speed)}")

        textView_wind_dir.text = getString(R.string.wind_direction)
            .plus(" ${showWindDirection(response?.wind?.deg)}")

    }

    private fun updatePressure(response: CurrentWeatherResponse?) {
        textView_pressure.text = getString(R.string.pressure)
            .plus(" ${response?.main?.pressure}")
    }

    private fun updateHumidity(response: CurrentWeatherResponse?) {
        textView_humidity.text = getString(R.string.humidity)
            .plus(" ${response?.main?.humidity}")
    }

    private fun updateToolbar() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Weather at the moment"
    }

    private fun updateToolbarSubtitle(response: CurrentWeatherResponse?) {
        if (response == null) return
        val dateI = response.dt.toString().plus("000")
        val date = Date(dateI.toLong())
        val format = SimpleDateFormat("HH:mm dd MMMM", Locale.getDefault())


//        val instant = Instant.ofEpochSecond(response.dt)
//        val zoneId = ZoneId.of(response.timezone.toString())
//        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)


        (activity as? AppCompatActivity)?.supportActionBar?.subtitle =
            "Updated ${format.format(date)}"

    }

}
