package hsj.shahram.weather.di.component

import dagger.BindsInstance
import dagger.Subcomponent
import hsj.shahram.weather.di.module.MainModule
import hsj.shahram.weather.view.MainActivity
import hsj.shahram.weather.view.adapter.CitiesListAdapter
import hsj.shahram.weather.view.adapter.DayWeekListAdapter


@Subcomponent(modules = [MainModule::class])
interface MainComponent {


    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder{

        fun build() : MainComponent

        @BindsInstance
        fun setCityAdapterListener(listener: CitiesListAdapter.OnCitiesClickListener) : Builder

        @BindsInstance
        fun setDayWeekAdapterListener(listener : DayWeekListAdapter.OnDayClickListener) : Builder



    }
}