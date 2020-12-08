package hsj.shahram.weather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hsj.shahram.weather.R
import hsj.shahram.weather.databinding.ActivityMainBinding
import hsj.shahram.weather.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding;
    private lateinit var viewModel : MainViewModel


    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater , R.layout.activity_main
            , null , false)

        setContentView(binding.root)

        setupViewModel()

        init()
    }

    // setup view model here
    fun setupViewModel()
    {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    private fun init() {

        setupHourRecyclerView()
        setupDayRecyclerView()

    }


    // set up horizontal recycler view
    fun setupHourRecyclerView()
    {
        binding.horRecyclerView.layoutManager = LinearLayoutManager(this , RecyclerView.HORIZONTAL
            , false)



    }


    // setup vertical recycler view
    fun setupDayRecyclerView()
    {
        binding.verRecyclerView.layoutManager = LinearLayoutManager(this)

    }




}