package com.example.terminal2020_shenaijia2018110532.ui.dashboard

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.terminal2020_shenaijia2018110532.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
//        val intent = Intent(this.requireContext(), DashboardActivity::class.java)
//        startActivity(intent)
        var adapter: CardAdapter = CardAdapter(game)
        recyclerView.adapter = adapter

        val configure = resources.configuration
        if(configure.orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.layoutManager = GridLayoutManager(this.requireContext(),4)
        }else{
            recyclerView.layoutManager = GridLayoutManager(this.requireContext(),6)
        }


        adapter.setOnClickListener {
            game.chooseCardAtIndex(it)
            adapter.notifyDataSetChanged()
            score.text = String.format("%s%d","Score:", game.score)
            score.text = "Score"+ game.score
        }

        adapter.notifyDataSetChanged()
        score.text = String.format("%s%d","Score:", game.score)
        score.text = "Score"+ game.score

        reset.setOnClickListener {
            game.reset()
            adapter.notifyDataSetChanged()
            score.text = String.format("%s%d","Score:", game.score)
            score.text = "Score"+ game.score
        }
    }

}