package hsj.shahram.weather.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import hsj.shahram.weather.di.module.DataRemoteModule
import hsj.shahram.weather.di.module.ViewModelModule
import hsj.shahram.weather.view.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DataRemoteModule::class , ViewModelModule::class])
interface AppComponent {


    fun getMainComponent () : MainComponent.Builder


    @Component.Factory
    interface Factory{

        fun create(@BindsInstance context : Context) : AppComponent

    }

}