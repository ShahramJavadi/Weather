package hsj.shahram.weather.view

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import hsj.shahram.weather.AppController
import hsj.shahram.weather.R
import hsj.shahram.weather.data.model.City
import hsj.shahram.weather.data.model.DailyWeather
import hsj.shahram.weather.data.model.TimeOfDay
import hsj.shahram.weather.data.remote.Status
import hsj.shahram.weather.databinding.ActivityMainBinding
import hsj.shahram.weather.databinding.CitiesListLayoutBinding
import hsj.shahram.weather.util.*
import hsj.shahram.weather.view.adapter.CitiesListAdapter
import hsj.shahram.weather.view.adapter.DayWeekListAdapter
import hsj.shahram.weather.view.adapter.HourListAdapter
import hsj.shahram.weather.viewmodel.MainViewModel
import hsj.shahram.weather.viewmodel.ViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity(), CitiesListAdapter.OnCitiesClickListener,
    DayWeekListAdapter.OnDayClickListener {


    private lateinit var cityDialog: AlertDialog

    @Inject
     lateinit var citiesListAdapter: CitiesListAdapter
    private lateinit var binding: ActivityMainBinding;
    private lateinit var viewModel: MainViewModel
    @Inject
     lateinit var hourListAdapter: HourListAdapter
    @Inject
     lateinit var dayWeekListAdapter: DayWeekListAdapter
    @Inject
    lateinit var factory: ViewModelFactory;


    val citiesObserver = Observer<List<City>> {


        citiesListAdapter.submitList(it)


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppController.getAppController().appComponent.getMainComponent().setCityAdapterListener(this)
            . setDayWeekAdapterListener(this).build().inject(this)

        setStatusBarColor()
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.activity_main, null, false
        )

        setContentView(binding.root)

        setupViewModel()

        init()
    }

    // setup view model here
    private fun setupViewModel() {

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

    }

    private fun init() {

        binding.viewModel = viewModel
        binding.currentTimeStamp = System.currentTimeMillis() / 1000

        clickListener()
        viewModel.findDefaultOrSelectedCity()
        defaultCityObserver()
        weatherCityObserver()



        setupHourRecyclerView()
        setupDayRecyclerView()

    }


    private fun showConnectionSnackBar(message: String?) {
        val snack: Snackbar
        snack = Snackbar.make(
            binding.root,
            message.let { it.toString() },
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(getString(R.string.retry)) {
                if (binding.city != null)
                    viewModel.getCityWeatherData(binding.city as City)

            }
        snack.show()

    }


    private fun clickListener() {

        viewModel.clickEvent.observe(this) {

            when {

                it.equals(MainViewModel.CITIES_FRAME_CLICKED) ->
                    showCitiesDialog()

                it.equals(MainViewModel.MENU_CLICKED) ->
                    openMenu()

            }

        }


    }


    private fun showCitiesDialog() {


        val citiesBinding: CitiesListLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.cities_list_layout, null, false
        )

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(citiesBinding.root)

        cityDialog = builder.create()
        cityDialog.show()

        setupCitiesRecyclerView(citiesBinding)


        viewModel.citiesLiveData.observe(this, citiesObserver)

        viewModel.getCities()


    }


    private fun setupCitiesRecyclerView(citiesBinding: CitiesListLayoutBinding) {

        val recyclerView: RecyclerView = citiesBinding.rvCities
        recyclerView.layoutManager = LinearLayoutManager(this)
      //  citiesListAdapter = CitiesListAdapter(this)
        recyclerView.adapter = citiesListAdapter


    }


    private fun weatherCityObserver() {

        viewModel.cityWeatherData.observe(this) {


            it?.let {

                when (it.status) {

                    Status.SUCCESS -> {
                        binding.isLoading = false

                        it.data?.let {
                            hourListAdapter.submitList(it.hourly)

                            dayWeekListAdapter.submitList(it.daily)

                            binding.iconCode = it.current.weather.get(0).icon
                            binding.weatherId = it.current.weather.get(0).id

                            binding.currentTemp = kToC(it.current.temp) + Const.DEGREE_SYMBOL
                            binding.maxMinTemp =
                                kToC(it.daily.get(0).temp.max) + Const.DEGREE_SYMBOL +
                                        "/" +
                                        kToC(it.daily.get(0).temp.min) + Const.DEGREE_SYMBOL
                        }


                    }


                    Status.ERROR -> {

                        binding.isLoading = false

                        showConnectionSnackBar(it.message)
                    }


                    Status.LOADING -> {


                        binding.isLoading = true


                    }

                }


            }


        }

    }


    private fun defaultCityObserver() {

        viewModel.defaultSelectedCityData.observe(this) {

            it.let {


                binding.city = it
                viewModel.getCityWeatherData(it)
            }

        }

    }


    // set up horizontal recycler view
    private fun setupHourRecyclerView() {
        binding.horRecyclerView.layoutManager = LinearLayoutManager(
            this, RecyclerView.HORIZONTAL, false
        )

   //     hourListAdapter = HourListAdapter()
        binding.horRecyclerView.adapter = hourListAdapter


    }


    // setup vertical recycler view
    private fun setupDayRecyclerView() {
        binding.verRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.verRecyclerView.isNestedScrollingEnabled = false
        dayWeekListAdapter = DayWeekListAdapter(this)
        binding.verRecyclerView.adapter = dayWeekListAdapter

    }

    override fun onCitiesClicked(city: City) {

        cityDialog.dismiss()
        viewModel.citiesLiveData.removeObserver(citiesObserver)
        binding.city = city
        viewModel.saveSelectedCity(city)
        viewModel.getCityWeatherData(city)

    }


    private fun setStatusBarColor() {

        val color: Int = getStatusBarColor(System.currentTimeMillis() / 1000)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, color)
        }

    }


    private fun openMenu() {

        val powerMenu = PowerMenu.Builder(this)
            .addItem(PowerMenuItem("Exit", false)) // add an item.
            .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
            .setMenuRadius(10f) // sets the corner radius.
            .setMenuShadow(10f) // sets the shadow.
            .setTextGravity(Gravity.CENTER)
            .setOnMenuItemClickListener(OnMenuItemClickListener(fun(position, item) { finish() }))
            .build()
        powerMenu.showAsAnchorCenter(binding.menu)

    }

    override fun onDayClicked(dailyWeather: DailyWeather) {

        val hourInt: Int = getHourFromTimeStamp(System.currentTimeMillis() / 1000, "HH").toInt()
        var temp: Double = 0.0

        when (hourInt) {

            in TimeOfDay.DAWN.hour -> temp = dailyWeather.temp.min
            in TimeOfDay.MORNING.hour -> temp = dailyWeather.temp.morn
            in TimeOfDay.NOON.hour -> temp = dailyWeather.temp.max
            in TimeOfDay.EVENING.hour -> temp = dailyWeather.temp.eve
            in TimeOfDay.NIGHT.hour -> temp = dailyWeather.temp.night


        }

        binding.iconCode = dailyWeather.weather.get(0).icon
        binding.weatherId = dailyWeather.weather.get(0).id

        binding.currentTemp = kToC(temp) + Const.DEGREE_SYMBOL
        binding.maxMinTemp = kToC(dailyWeather.temp.max) + Const.DEGREE_SYMBOL +
                "/" +
                kToC(dailyWeather.temp.min) + Const.DEGREE_SYMBOL


    }


}