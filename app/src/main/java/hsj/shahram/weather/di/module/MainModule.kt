package hsj.shahram.weather.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import hsj.shahram.weather.view.MainActivity
import hsj.shahram.weather.view.adapter.CitiesListAdapter
import hsj.shahram.weather.view.adapter.DayWeekListAdapter

@Module
 class MainModule {

    @Provides
     fun provideCityAdapter( listener : CitiesListAdapter.OnCitiesClickListener) : CitiesListAdapter{

         return CitiesListAdapter(listener)

     }

    @Provides
    fun provideDayWeekListAdapter( listener : DayWeekListAdapter.OnDayClickListener) : DayWeekListAdapter{

        return DayWeekListAdapter(listener)

    }

}