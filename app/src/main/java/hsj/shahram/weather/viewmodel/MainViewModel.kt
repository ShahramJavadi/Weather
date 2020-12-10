package hsj.shahram.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import hsj.shahram.weather.data.City
import hsj.shahram.weather.data.Weather
import hsj.shahram.weather.repository.MainRepo

class MainViewModel : ViewModel() {


    var citiesLiveData: LiveData<List<City>>
    var defaultSelectedCityData: LiveData<City>
    var cityWeatherData: LiveData<Weather>
    var clickEvent : LiveEvent<String>  = LiveEvent()
    var repo: MainRepo

    companion object{

        @JvmStatic
        val CITIES_FRAME_CLICKED = "Cities_Frame_Clicked"
        @JvmStatic
        val MENU_CLICKED = "Menu_Clicked"

    }


    init {

        repo = MainRepo()
        citiesLiveData = repo.citiesLiveData
        defaultSelectedCityData = repo.defaultSelectedCityData
        cityWeatherData = repo.cityWeatherData


    }

    // request for obtaining all cities from repo
    fun getCities() {

        repo.getCities()

    }


    fun saveSelectedCity(city: City)
    {

        repo.saveSelectedCity(city)

    }

    fun getCityWeatherData(city : City)
    {

        repo.getWeatherCityData(city)

    }

    fun findDefaultOrSelectedCity()
    {

        repo.findDefaultOrSelectedCity()

    }

    // set event click on corresponding field
    fun setClickEvent(event : String)
    {

        clickEvent.postValue(event)

    }



}