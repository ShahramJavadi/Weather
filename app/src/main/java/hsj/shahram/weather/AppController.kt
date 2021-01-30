package hsj.shahram.weather

import android.app.Application
import hsj.shahram.weather.di.component.AppComponent
import hsj.shahram.weather.di.component.DaggerAppComponent

class AppController : Application() {


    val appComponent : AppComponent by lazy {


        DaggerAppComponent.factory().create(this)


    }


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