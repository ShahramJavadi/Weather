package hsj.shahram.weather.util

import android.content.Context
import android.content.SharedPreferences
import hsj.shahram.weather.AppController

fun getPreferenceInstance() : SharedPreferences{

   return AppController.getAppController().getSharedPreferences(Const.SHARED_PREF_NAME , Context.MODE_PRIVATE)
}