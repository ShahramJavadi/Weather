package hsj.shahram.weather.util

class Pref {

    companion object{

        val SELECTED_CITY_ID = "Selected_City_Id"
        val SELECTED_CITY_NAME = "Selected_City_Name"
        val SELECTED_CITY_LAT = "Selected_City_Lat"
        val SELECTED_CITY_LON = "Selected_City_Lon"


        fun putString(key : String , value : String?) =
            getPreferenceInstance().edit().putString(key , value).apply()






        fun getString(key : String)
         = getPreferenceInstance().getString(key , "")


    }



}