package hsj.shahram.weather

import android.app.Application

class AppController : Application() {


    init {
        instance = this
    }

    companion object {

        private var instance: AppController? = null

        fun getAppController(): AppController {
            return instance as AppController

        }


    }

}