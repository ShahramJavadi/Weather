package hsj.shahram.weather.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import hsj.shahram.weather.di.keys.ViewModelKey
import hsj.shahram.weather.viewmodel.MainViewModel
import hsj.shahram.weather.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(factory : ViewModelFactory) :ViewModelProvider.Factory;

    @Binds
    @IntoMap
    @ViewModelKey( MainViewModel::class)
    abstract fun provideMap(m : MainViewModel) : ViewModel

}
