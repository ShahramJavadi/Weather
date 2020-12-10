package hsj.shahram.weather.util

import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateUtils
import hsj.shahram.weather.AppController
import hsj.shahram.weather.R
import hsj.shahram.weather.data.TimeOfDay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun getPreferenceInstance(): SharedPreferences =

    AppController.getAppController()
        .getSharedPreferences(Const.SHARED_PREF_NAME, Context.MODE_PRIVATE)


// this function convert kelvin to celsius
fun kToC(kelvin: Double) = ((kelvin - 273.15F).toInt()).toString()


fun getHourFromTimeStamp(t: Long , pattern:String): String {

    var sdf: SimpleDateFormat = SimpleDateFormat(pattern, Locale.US)
    val date: Date = Date(t * 1000);
    return sdf.format(date).toString();


}


// get day of week name (ex: Monday)
fun getDayOfWeekName(timeStamp: Long): String {


    if(isToday(timeStamp * 1000))
        return "Today"


    if(isTomorrow(timeStamp * 1000))
        return "Tomorrow"


    val sdf = SimpleDateFormat("EEEE")
    val d = Date(timeStamp * 1000)
    return sdf.format(d).toString()


}

fun isToday(whenInMillis: Long) = DateUtils.isToday(whenInMillis)


fun isTomorrow(whenInMillis: Long) = DateUtils.isToday(whenInMillis - DateUtils.DAY_IN_MILLIS)


fun getStatusBarColor(hour: Long) : Int {

    val hourInt: Int = getHourFromTimeStamp(hour, "HH").toInt()
    var color: Int = 0

    when (hourInt) {
        in TimeOfDay.DAWN.hour -> color = R.color.dawnTopGradientColor
        in TimeOfDay.MORNING.hour -> color = R.color.morningTopGradientColor
        in TimeOfDay.NOON.hour ->  color = R.color.noonTopGradientColor
        in TimeOfDay.EVENING.hour ->  color = R.color.eveningTopGradientColor
        in TimeOfDay.NIGHT.hour ->  color = R.color.nightTopGradientColor
    }

    return color
}





