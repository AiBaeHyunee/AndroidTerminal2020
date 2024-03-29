package com.example.terminal2020_shenaijia2018110532.ui.notifications

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.terminal2020_shenaijia2018110532.MusicService
import com.example.terminal2020_shenaijia2018110532.R
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlin.concurrent.thread
const val MyReceiverAction = "com.example.musicplayer_shenaijia1123.newmusic"

class NotificationActivity : AppCompatActivity() , ServiceConnection {
    val TAG = "MusicService"

    var binder: MusicService.MusicBinder? = null

    lateinit var receiver: MusicReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notifications)

        val intentFilter = IntentFilter()
        intentFilter.addAction(MyReceiverAction)
        receiver = MusicReceiver()
        registerReceiver(receiver, intentFilter)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        } else {
            startMusicService()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binder?.currentPosition = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    fun startMusicService() {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra(MusicService.Commond, 1)
        startService(intent)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    fun onPlay(v: View) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra(MusicService.Commond, 1)
        startService(intent)
    }

    fun onPause(v: View) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra(MusicService.Commond, 2)
        startService(intent)
    }

    fun onStop(v: View) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra(MusicService.Commond, 3)
        startService(intent)
    }

    fun onNext(v: View) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra(MusicService.Commond, 4)
        startService(intent)
    }

    fun onPrev(v: View) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra(MusicService.Commond, 5)
        startService(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        startMusicService()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        binder = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        binder = service as MusicService.MusicBinder

        thread {
            while (binder != null) {
                Thread.sleep(1000)
                runOnUiThread {
                    seekBar.max = binder!!.duration
                    seekBar.progress = binder!!.currentPosition
                    textView_count.text = "${binder!!.currentIndex + 1}/${binder?.size}"
                    textView_mn.text = binder!!.musicName
                }
            }
        }
    }

    inner class MusicReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val op: Int = intent.getIntExtra("operate", 0)
            if (op == 1) {
                seekBar.max = binder!!.duration
                textView_mn.text = binder!!.musicName
            }
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        val intent = Intent(this, MusicService::class.java)
        unregisterReceiver(receiver)
        unbindService(this)
    }

}