package hsj.shahram.weather.util

import android.os.Build
import hsj.shahram.weather.BuildConfig
import hsj.shahram.weather.data.Weather
import okhttp3.CertificatePinner
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface ApiService {


    companion object {
        fun unSafeOkHttpClient() :OkHttpClient.Builder {
            val okHttpClient = OkHttpClient.Builder()
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts:  Array<TrustManager> = arrayOf(object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?){}
                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate>  = arrayOf()
                })

                // Install the all-trusting trust manager
                val  sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                if (trustAllCerts.isNotEmpty() &&  trustAllCerts.first() is X509TrustManager) {
                    okHttpClient.sslSocketFactory(sslSocketFactory, trustAllCerts.first() as X509TrustManager)
                    okHttpClient.hostnameVerifier { _, _ -> true }
                }

                return okHttpClient
            } catch (e: Exception) {
                return okHttpClient
            }
        }

        fun create(): ApiService {

            val retrofit = Retrofit.Builder()

                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .client(unSafeOkHttpClient().build())
                .baseUrl(BuildConfig.SERVER_URL)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }


    @GET("data/2.5/onecall")
    suspend fun getCityWeatherData(
        @Query("appid") apiKey: String?,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?
    ): Weather


}