package hsj.shahram.weather.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.weather.R
import hsj.shahram.weather.data.model.HourlyWeather
import hsj.shahram.weather.databinding.HourlyWeatherItemBinding
import javax.inject.Inject

class HourListAdapter  @Inject constructor()
    : RecyclerView.Adapter<HourListAdapter.HourlyHolder>() {

    private var list : List<HourlyWeather> = ArrayList<HourlyWeather>()

    fun submitList(list : List<HourlyWeather>)
    {

        if(list.size == 0)
            return

        this.list = list.subList(0 , 24)
        notifyDataSetChanged()

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HourlyHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context) ,
                R.layout.hourly_weather_item , parent , false))





    override fun onBindViewHolder(holder: HourlyHolder, position: Int) {

        holder.bindTo(list.get(position))


    }

    override fun getItemCount() = list.size



    class HourlyHolder(var binding : HourlyWeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bindTo(hourlyWeather : HourlyWeather)
        {

            binding.hourlyWeather = hourlyWeather

            binding.currentTimeStamp = System.currentTimeMillis() / 1000

        }


    }


}