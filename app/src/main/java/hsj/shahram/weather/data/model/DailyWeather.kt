package hsj.shahram.weather.data.model

data class DailyWeather(var dt : Long, var weather : List<WeatherType>, var temp : Temp) {
}