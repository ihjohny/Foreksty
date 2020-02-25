package com.aappeye.foreksty.ui.weather.week

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aappeye.foreksty.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_week_weather.*
import javax.inject.Inject


class WeekWeatherFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: WeekWeatherViewModelFactory
    private lateinit var viewModel: WeekWeatherViewModel
    private lateinit var weekWeatherAdapter: WeekWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeekWeatherViewModel::class.java)
        bindUi()

        return inflater.inflate(R.layout.fragment_week_weather, container, false)
    }

    private fun bindUi() {

        viewModel.fetchData()

        viewModel.dailyWeatherList.observe(viewLifecycleOwner, Observer {

            week_fetch_id.visibility = View.GONE
            week_recyclerView_id.visibility = View.VISIBLE

            if(it == null || it.isEmpty()) return@Observer

            weekWeatherAdapter = WeekWeatherAdapter(it.subList(1, it.size), viewModel.isMetricUnit)
            week_recyclerView_id.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = weekWeatherAdapter
            }

        })

    }

}
