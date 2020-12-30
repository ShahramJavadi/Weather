package hsj.shahram.weather.viewmodel

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import hsj.shahram.weather.data.City
import hsj.shahram.weather.data.Weather
import hsj.shahram.weather.repository.MainRepo
import hsj.shahram.weather.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {


    var citiesLiveData: LiveData<List<City>>
    var defaultSelectedCityData: LiveData<City>
    var cityWeatherData: MutableLiveData<Resources<Weather>>
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
        cityWeatherData = MutableLiveData()


    }

    // request for obtaining all cities from repo
    fun getCities() {

        repo.getCities()

    }


    fun saveSelectedCity(city: City)
    {

        repo.saveSelectedCity(city)

    }

    fun getCityWeatherData(city : City) {
       viewModelScope.launch(IO) {


            cityWeatherData.postValue(Resources.loading(null))

           try {

               cityWeatherData.postValue(Resources.success(repo.getWeatherCityData(city)))

           }catch (e : Exception)
           {

               cityWeatherData.postValue(Resources.error(null , e.message ?: "Unknown Error"))

           }




       }

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