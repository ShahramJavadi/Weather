package hsj.shahram.weather.data

import hsj.shahram.weather.util.kToC

data class HourlyWeather(var dt : Long , var temp : Double , var weather : List<WeatherType>) {



}