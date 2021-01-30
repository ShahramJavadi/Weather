package hsj.shahram.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hsj.shahram.weather.repository.MainRepo
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider
import java.util.Map.Entry;
import java.util.Map.entry

class ViewModelFactory @Inject constructor(val map: Map<Class<out ViewModel>
        , @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        var creator: Provider<out ViewModel>? = map.get(modelClass);

        if (creator == null) {

            for ((key, value) in map) {


                if (modelClass.isAssignableFrom(key))
                    creator = value

            }
        }

        if (creator == null)
            throw IllegalArgumentException("Unknown ViewModel")

        try {

            return creator.get() as T

        } catch (e: Exception) {

            throw RuntimeException(e)

        }
    }
}