package com.example.terminal2020_shenaijia2018110532.ui.weather

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.terminal2020_shenaijia2018110532.R
import com.example.terminal2020_shenaijia2018110532.MainActivity2
import kotlinx.android.synthetic.main.weather_fragment.*

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.cities.observe(viewLifecycleOwner, Observer {
            val cities = it
            val adapter = ArrayAdapter<CityItem>(this.requireContext(),android.R.layout.simple_list_item_1,cities)
            listView.adapter = adapter
            listView.setOnItemClickListener {_, _, position, _ ->
                val cityCode = cities[position].city_code
                val intent = Intent(this.requireContext(),MainActivity2::class.java)
//                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("city_code",cityCode)
                startActivity(intent)
            }
        })
    }

}