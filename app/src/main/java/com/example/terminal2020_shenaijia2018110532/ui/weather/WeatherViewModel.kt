package com.example.terminal2020_shenaijia2018110532.ui.weather

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.terminal2020_shenaijia2018110532.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val _cities:MutableLiveData<List<CityItem>> = MutableLiveData()

    val cities :LiveData<List<CityItem>> = _cities

    init {
        thread {
            var str = readFileFromRaw(R.raw.citycode)
            val gson = Gson()
            val CityType = object : TypeToken<List<CityItem>>(){}.type
            var cts:List<CityItem> = gson.fromJson(str,CityType)
            cts = cts.filter { it.city_code!=""}
            _cities.postValue(cts)
        }
    }

    fun readFileFromRaw(rawName: Int): String? {
        try {
            val inputReader = InputStreamReader(getApplication<Application>().resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result: String? = ""
            while (bufReader.readLine().also({ line = it }) != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}