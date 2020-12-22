package com.example.terminal2020_shenaijia2018110532.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.terminal2020_shenaijia2018110532.ui.dashboard.model.CardMatchingGame

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
    var game: CardMatchingGame = CardMatchingGame(24)
}