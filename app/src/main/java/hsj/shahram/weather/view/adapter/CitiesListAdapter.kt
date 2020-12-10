package hsj.shahram.weather.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.weather.data.City
import hsj.shahram.weather.R
import hsj.shahram.weather.databinding.CityItemBinding

class CitiesListAdapter(var clickListener: OnCitiesClickListener)
    : RecyclerView.Adapter<CitiesListAdapter.CitiesHolder>() {

    private var cityList : List<City> = ArrayList<City>()

    fun submitList(cityList : List<City>)
    {

        if(cityList.size == 0)
            return

        this.cityList = cityList
        notifyDataSetChanged()

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CitiesHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) ,
            R.layout.city_item , parent , false))





    override fun onBindViewHolder(holder: CitiesHolder, position: Int) {

        holder.bindTo(cityList.get(position))
        holder.setClickListener(clickListener)


    }

    override fun getItemCount() = cityList.size



    class CitiesHolder(var binding : CityItemBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bindTo(city: City)
        {

            binding.cityModel = city

        }

        fun setClickListener(clickListener: OnCitiesClickListener)
        {

            binding.clickListener = clickListener

        }

    }


    interface OnCitiesClickListener{

        fun onCitiesClicked(city: City)

    }

}