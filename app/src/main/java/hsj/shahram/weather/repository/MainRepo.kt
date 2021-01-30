package hsj.shahram.weather.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hsj.shahram.weather.AppController
import hsj.shahram.weather.BuildConfig
import hsj.shahram.weather.data.model.City
import hsj.shahram.weather.R
import hsj.shahram.weather.data.model.Coordinate
import hsj.shahram.weather.data.model.Weather
import hsj.shahram.weather.data.remote.ApiService
import hsj.shahram.weather.util.Pref
import java.io.InputStreamReader
import java.io.Reader
import javax.inject.Inject


class MainRepo @Inject constructor(
    val context: Context,
    val apiService: ApiService, val pref: Pref
) {


    var citiesLiveData: MutableLiveData<List<City>> = MutableLiveData()
    var defaultSelectedCityData: MutableLiveData<City> = MutableLiveData()
    var cityWeatherData: MutableLiveData<Weather> = MutableLiveData()


    // this function retrieve selected city by user or default city(Tehran) if user not selected any city yet
    fun findDefaultOrSelectedCity() {

        val selectedCityId = pref.getString(pref.SELECTED_CITY_ID)

        if (selectedCityId.equals("")) {

            setDefaultCity()

        }


        val coordinate = Coordinate(
            pref.getString(pref.SELECTED_CITY_LAT)?.toDouble(),
            pref.getString(pref.SELECTED_CITY_LON)?.toDouble()
        )

        var city: City = City(
            pref.getString(pref.SELECTED_CITY_ID)?.toLong(),
            pref.getString(pref.SELECTED_CITY_NAME),
            coordinate
        )


        defaultSelectedCityData.postValue(city)

    }


    // save selected city in preference
    fun saveSelectedCity(city: City) {
        pref.putString(pref.SELECTED_CITY_ID, city.id.toString())
        pref.putString(pref.SELECTED_CITY_NAME, city.name)
        pref.putString(pref.SELECTED_CITY_LON, city.coord?.lon.toString())
        pref.putString(pref.SELECTED_CITY_LAT, city.coord?.lat.toString())


    }

    suspend fun getWeatherCityData(city: City) =

     apiService.getCityWeatherData(
            BuildConfig.API_KEY,
            city.coord?.lat?.toDouble(),
            city.coord?.lon?.toDouble()
        )


    // set default city (Tehran)
    private fun setDefaultCity() {

        pref.putString(pref.SELECTED_CITY_ID, "112931")
        pref.putString(pref.SELECTED_CITY_NAME, "Tehran")
        pref.putString(pref.SELECTED_CITY_LON, "51.421509")
        pref.putString(pref.SELECTED_CITY_LAT, "35.694389")

    }

    // get json file from raw folder
    fun getCities() {

        val reader: Reader = InputStreamReader(
           context.resources.openRawResource(
                R.raw.extracted_cities
            )
        )

        val turnsType = object : TypeToken<List<City>>() {}.type


        val cityList: List<City> = Gson().fromJson(reader, turnsType)
        Log.d("TAG", "getCities: " + cityList.toString())
        citiesLiveData.postValue(cityList)


    }


}