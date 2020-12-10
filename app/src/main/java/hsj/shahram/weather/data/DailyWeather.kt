package hsj.shahram.weather.data

import hsj.shahram.weather.data.WeatherType

data class DailyWeather(var dt : Long , var weather : List<WeatherType> ,var temp : Temp) {
}