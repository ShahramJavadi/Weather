package hsj.shahram.weather.data.model

data class CurrentWeather(var dt : Long ,var temp : Double , var weather : List<WeatherType>) {
}