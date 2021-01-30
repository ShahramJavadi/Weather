package hsj.shahram.weather.data.model

 data class Weather(var current : CurrentWeather, var hourly: List<HourlyWeather>
                    , var daily : List<DailyWeather>) {
}