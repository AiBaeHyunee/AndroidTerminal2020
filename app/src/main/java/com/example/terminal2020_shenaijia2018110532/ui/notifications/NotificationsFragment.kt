package com.example.terminal2020_shenaijia2018110532.ui.notifications

import android.Manifest
import android.content.*
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.terminal2020_shenaijia2018110532.R
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.io.IOException
import kotlin.concurrent.thread

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    val mediaPlayer = MediaPlayer()
    val musicList = mutableListOf<String>()
    val musicNameList = mutableListOf<String>()
    var current = 0
    var isPause = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val intent = Intent(this.requireContext(), NotificationActivity::class.java)
        startActivity(intent)

    }


}