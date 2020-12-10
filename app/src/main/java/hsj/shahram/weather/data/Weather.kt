package hsj.shahram.weather.data

 data class Weather(var current : CurrentWeather , var hourly: List<HourlyWeather>
                    , var daily : List<DailyWeather>) {
}