package com.example.terminal2020_shenaijia2018110532.ui.dashboard

import android.content.res.Configuration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.GridLayoutManager
import com.example.cardgame_shenaijia.model.CardMatchingGame
import com.example.terminal2020_shenaijia2018110532.R

import kotlinx.android.synthetic.main.fragment_dashboard.*

var game:CardMatchingGame = CardMatchingGame(24)

class DashboardActivity : AppCompatActivity() {

    lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dashboard)

        adapter = CardAdapter(game)
        recyclerView.adapter = adapter

        val configure = resources.configuration
        if(configure.orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.layoutManager = GridLayoutManager(this,4)
        }else{
            recyclerView.layoutManager = GridLayoutManager(this,6)
        }


        adapter.setOnClickListener {
            game.chooseCardAtIndex(it)
            updateUI()
        }

        updateUI()

        reset.setOnClickListener {
            game.reset()
            updateUI()
        }
    }

    fun updateUI(){
        adapter.notifyDataSetChanged()
        score.text = String.format("%s%d","Score:", game.score)
        score.text = "Score"+ game.score
    }

}