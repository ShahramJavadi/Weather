package hsj.shahram.weather.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hsj.shahram.weather.AppController
import hsj.shahram.weather.BuildConfig
import hsj.shahram.weather.data.City
import hsj.shahram.weather.R
import hsj.shahram.weather.data.Coordinate
import hsj.shahram.weather.data.Weather
import hsj.shahram.weather.util.ApiService
import hsj.shahram.weather.util.Pref
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import javax.security.auth.callback.Callback


class MainRepo {


    var citiesLiveData: MutableLiveData<List<City>> = MutableLiveData()
    var defaultSelectedCityData: MutableLiveData<City> = MutableLiveData()
    var cityWeatherData: MutableLiveData<Weather> = MutableLiveData()


    // this function retrieve selected city by user or default city(Tehran) if user not selected any city yet
    fun findDefaultOrSelectedCity() {

        val selectedCityId = Pref.getString(Pref.SELECTED_CITY_ID)

        if (selectedCityId.equals("")) {

            setDefaultCity()

        }


        val coordinate = Coordinate(
            Pref.getString(Pref.SELECTED_CITY_LAT)?.toDouble(),
            Pref.getString(Pref.SELECTED_CITY_LON)?.toDouble()
        )

        var city: City = City(
            Pref.getString(Pref.SELECTED_CITY_ID)?.toLong(),
            Pref.getString(Pref.SELECTED_CITY_NAME),
            coordinate
        )


        defaultSelectedCityData.postValue(city)

    }



    // save selected city in preference
    fun saveSelectedCity(city: City)
    {
        Pref.putString(Pref.SELECTED_CITY_ID, city.id.toString())
        Pref.putString(Pref.SELECTED_CITY_NAME, city.name)
        Pref.putString(Pref.SELECTED_CITY_LON, city.coord?.lon.toString())
        Pref.putString(Pref.SELECTED_CITY_LAT, city.coord?.lat.toString())


    }

   suspend fun getWeatherCityData(city: City) =

        ApiService.create().getCityWeatherData(
            BuildConfig.API_KEY,
            city.coord?.lat?.toDouble(),
            city.coord?.lon?.toDouble())





    // set default city (Tehran)
    private fun setDefaultCity() {

        Pref.putString(Pref.SELECTED_CITY_ID, "112931")
        Pref.putString(Pref.SELECTED_CITY_NAME, "Tehran")
        Pref.putString(Pref.SELECTED_CITY_LON, "51.421509")
        Pref.putString(Pref.SELECTED_CITY_LAT, "35.694389")

    }

    // get json file from raw folder
    fun getCities() {

        val reader: Reader = InputStreamReader(
            AppController.getAppController().resources.openRawResource(
                R.raw.extracted_cities
            )
        )

        val turnsType = object : TypeToken<List<City>>() {}.type


        val cityList: List<City> = Gson().fromJson(reader, turnsType)
        Log.d("TAG", "getCities: " + cityList.toString())
        citiesLiveData.postValue(cityList)


    }


}