package com.serg.simplewidgetforecast.data.network


import com.serg.simplewidgetforecast.data.response.CurrentWeatherResponse

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "f0d74b68a46278cece7489b2e76664f7"

// "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude +
//                                "&lon=" + longitude +
//                                "&APPID=f0d74b68a46278cece7489b2e76664f7&lang=ru&units=metric"
interface OpenWeatherMapApiService {
    //    @GET("weather?")
//    fun getCurrentWeather(
//        @Query("lang") language: String,
//        @Query("units") units: String = "metric"
//    )
    @GET("weather")
    suspend fun getCurrentWeatherByCityAsync(
        @Query("q") city: String = "Moscow",
        @Query("APPID") appId: String = API_KEY,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse

    @GET("weather")
    suspend fun getCurrentWeatherByCoordAsync(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("APPID") appId: String = API_KEY,
        @Query("lang") language: String = "ru",
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse

//    @GET("forecast")
//    fun getWeatherForecast(
//        @Query("lat") latitude: Double,
//        @Query("lon") longitude: Double,
//        @Query("APPID") appid: String = API_KEY,
//        @Query("lang") language: String = "ru",
//        @Query("units") units: String = "metric"
//    ): Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): OpenWeatherMapApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter(
                        "APPID",
                        API_KEY
                    )
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                //.addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                //.addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherMapApiService::class.java)
        }
    }
}