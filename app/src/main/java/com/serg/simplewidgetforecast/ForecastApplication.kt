package com.serg.simplewidgetforecast

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.serg.simplewidgetforecast.data.db.ForecastDatabase
import com.serg.simplewidgetforecast.data.network.*
import com.serg.simplewidgetforecast.data.provider.LocationProvider
import com.serg.simplewidgetforecast.data.provider.LocationProviderImpl
import com.serg.simplewidgetforecast.data.provider.UnitProvider
import com.serg.simplewidgetforecast.data.provider.UnitProviderImpl
import com.serg.simplewidgetforecast.data.repository.ForecastRepository
import com.serg.simplewidgetforecast.data.repository.ForecastRepositoryImpl
import com.serg.simplewidgetforecast.ui.MainActivity
import com.serg.simplewidgetforecast.ui.current.CurrentWeatherFragment
import com.serg.simplewidgetforecast.ui.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton {
            ForecastDatabase(instance())
        }

        bind() from singleton {
            instance<ForecastDatabase>().currentWeatherDao()
        }

        bind<ConnectivityInterceptor>() with singleton {
            ConnectivityInterceptorImpl(instance())
        }

        bind() from singleton {
            OpenWeatherMapApiService(instance())
        }

        //Added 2 instances
        bind<WeatherNetworkDataSource>() with singleton {
            WeatherNetworkDataSourceImpl(instance(), instance())
        }

        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }

        bind<LocationProvider>() with singleton {
            LocationProviderImpl(instance(), instance())
        }

        bind<ForecastRepository>() with singleton {
            ForecastRepositoryImpl(instance(), instance(), instance())
        }

        bind<UnitProvider>() with singleton {
            UnitProviderImpl(instance())
        }

        bind() from provider {
            CurrentWeatherViewModelFactory(instance(), instance())
        }

        //My
//        bind<AppCompatActivity>() with singleton {
//            MainActivity()
//        }


    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}