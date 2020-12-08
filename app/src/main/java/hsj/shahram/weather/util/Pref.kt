package hsj.shahram.weather.util

class Pref {

    companion object{

        val DEFAULT_CITY = "DefaultCity"


        fun putString(key : String , value : String?) =
            getPreferenceInstance().edit().putString(key , value).apply()






        fun getString(key : String)
         = getPreferenceInstance().getString(key , "")


    }



}