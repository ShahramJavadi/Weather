package hsj.shahram.weather.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Pref @Inject constructor(context: Context){

        private val pref : SharedPreferences = context.getSharedPreferences(Const.SHARED_PREF_NAME ,
        Context.MODE_PRIVATE)

        val SELECTED_CITY_ID = "Selected_City_Id"
        val SELECTED_CITY_NAME = "Selected_City_Name"
        val SELECTED_CITY_LAT = "Selected_City_Lat"
        val SELECTED_CITY_LON = "Selected_City_Lon"


        fun putString(key : String , value : String?) =
            pref.edit().putString(key , value).apply()






        fun getString(key : String)
         = pref.getString(key , "")






}