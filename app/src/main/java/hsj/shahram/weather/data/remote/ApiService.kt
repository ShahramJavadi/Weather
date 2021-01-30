package hsj.shahram.weather.data.remote

import hsj.shahram.weather.BuildConfig
import hsj.shahram.weather.data.model.Weather
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface ApiService {


    @GET("data/2.5/onecall")
    suspend fun getCityWeatherData(
        @Query("appid") apiKey: String?,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?
    ): Weather


}