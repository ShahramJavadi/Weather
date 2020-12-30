package hsj.shahram.weather

import android.app.Application

class AppController : Application() {


    init {

        instance = this

    }

    companion object {

        private lateinit var instance: AppController

        fun getAppController(): AppController {
            return instance

        }


    }



}