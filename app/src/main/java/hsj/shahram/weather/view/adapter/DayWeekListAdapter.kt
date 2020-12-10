package hsj.shahram.weather.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.weather.R
import hsj.shahram.weather.data.City
import hsj.shahram.weather.data.DailyWeather
import hsj.shahram.weather.databinding.CityItemBinding
import hsj.shahram.weather.databinding.DailyWeatherItemBinding
import hsj.shahram.weather.util.Const
import hsj.shahram.weather.util.kToC

class DayWeekListAdapter(var clickListener: OnDayClickListener) : RecyclerView.Adapter<DayWeekListAdapter.DayWeekHolder>() {

    private var dailyList : List<DailyWeather> = ArrayList<DailyWeather>()


    fun submitList(dailyList : List<DailyWeather>)
    {
        if(dailyList.size == 0)
            return

        this.dailyList = dailyList
        notifyDataSetChanged()

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayWeekHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context) ,
            R.layout.daily_weather_item , parent , false))





    override fun onBindViewHolder(holder: DayWeekHolder, position: Int) {

        holder.bindTo(dailyList.get(position))
        holder.setClickListener(clickListener)


    }

    override fun getItemCount() = dailyList.size



    class DayWeekHolder(var binding : DailyWeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bindTo(dailyWeather: DailyWeather)
        {

            binding.dailyModel = dailyWeather
            binding.currentTimeStamp = System.currentTimeMillis() / 1000


        }

        fun setClickListener(onDayClickListener: OnDayClickListener)
        {

            binding.clickListener = onDayClickListener

        }


    }


    interface OnDayClickListener{

        fun onDayClicked(dailyWeather: DailyWeather)
    }


}