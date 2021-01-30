package hsj.shahram.weather.util

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hsj.shahram.weather.AppController
import hsj.shahram.weather.R
import hsj.shahram.weather.data.model.TimeOfDay

object BindingAdapterMethods {
    @BindingAdapter("loadBackground")
    @JvmStatic
    fun loadBackgroundImage(mainView: ConstraintLayout, hour: String) {

        var background: Int = 0
        val hourInt: Int = hour.toInt()

        when (hourInt) {
            in TimeOfDay.DAWN.hour -> background = R.drawable.dawn_gradiant_background
            in TimeOfDay.MORNING.hour -> background = R.drawable.morning_gradiant_background
            in TimeOfDay.NOON.hour -> background = R.drawable.noon_gradiant_background
            in TimeOfDay.EVENING.hour -> background = R.drawable.evening_gradiant_background
            in TimeOfDay.NIGHT.hour -> background = R.drawable.night_gradiant_background
        }


        mainView.background =
            ContextCompat.getDrawable(AppController.getAppController(), background)

    }


    // we did not use this method
    // but we can use the method and load icons from openweathermap.com instead of loading statically
    @BindingAdapter("loadIconFromApi")
    @JvmStatic
    fun loadIconFromApi(imageView: ImageView, iconCode: String?) {

        val url = Const.ICON_BASE_URL + iconCode + "@4x.png"

        Glide.with(imageView.context).load(url).skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)

    }

    // we know that our drawable not supporting all of weather state like `Drizzle` and ...
    // thus we load icon from api in else branch
    @BindingAdapter(value = ["weatherId", "hour", "iconCode"], requireAll = false)
    @JvmStatic
    fun loadIcon(imageView: ImageView, id: Long?, hour: String?, iconCode: String?) {
        var background: Int = 0
        val hourInt: Int? = hour?.toInt()
        val stringId: String = id.toString()
        val url = Const.ICON_BASE_URL + iconCode + "@4x.png"



        when {

            stringId.startsWith("2") -> background = R.drawable.ic____thunderstorm
            stringId.startsWith("5") -> background = R.drawable.ic___shower_raint
            stringId.startsWith("6") -> background = R.drawable.ic___snow
            stringId.startsWith("7") -> background = R.drawable.ic___mist

            stringId.equals("800") -> when (hourInt) {
                in TimeOfDay.DAWN.hour -> background = R.drawable.ic___clear_sky_dawn
                in TimeOfDay.MORNING.hour -> background = R.drawable.ic___clear_sky_morning
                in TimeOfDay.NOON.hour -> background = R.drawable.ic___clear_sky_morning
                in TimeOfDay.EVENING.hour -> background = R.drawable.ic___clear_sky_evening
                in TimeOfDay.NIGHT.hour -> background = R.drawable.ic___clear_sky_night
            }

            stringId.equals("801") -> when (hourInt) {
                in TimeOfDay.DAWN.hour -> background = R.drawable.ic___few_cloud_dawn
                in TimeOfDay.MORNING.hour -> background = R.drawable.ic___few_cloud_morning
                in TimeOfDay.NOON.hour -> background = R.drawable.ic___few_cloud_morning
                in TimeOfDay.EVENING.hour -> background = R.drawable.ic___few_cloud_evening
                in TimeOfDay.NIGHT.hour -> background = R.drawable.ic___few_clound_night
            }

            stringId.equals("802") -> background = R.drawable.ic___scattered_clouds
            stringId.equals("803") -> background = R.drawable.ic___broken_cloud

            // load from api
            else -> Glide.with(imageView.context).load(url).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)


        }


        // load from local
        if (background != 0) {
            Glide.with(imageView.context).load(background).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
        }


    }



}
