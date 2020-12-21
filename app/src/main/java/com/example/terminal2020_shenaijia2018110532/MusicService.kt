package com.example.terminal2020_shenaijia2018110532

import android.app.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MusicService : Service() {
    val TAG = "MusicService"
    companion object {
        val Commond = "Operate"
    }
    val mediaPlayer = MediaPlayer()
    val musicList = mutableListOf<String>()
    val musicNameList = mutableListOf<String>()
    var current = 0
    var isPause = false

    val binder = MusicBinder()

    inner class MusicBinder:Binder(){
        val musicName
                get() = musicNameList[current]
        var currentPosition
                get() = mediaPlayer.currentPosition
                set(value) = mediaPlayer.seekTo(value)

        val duration
                get() = mediaPlayer.duration

        val size
                get() = musicList.size

        val currentIndex
                get() = current

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        getMusicList()

        mediaPlayer.setOnPreparedListener{
            it.start()
        }
        mediaPlayer.setOnCompletionListener {
            current++
            if(current>=musicList.size){
                current = 0
            }
            play()
        }

//        val builder: Notification.Builder
//        val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.0){
//            val channel = NotificationChannel(MyChannel, "test", NotificationManager.IMPORTANCE_MIN)
//            notificationManager.createNotificationChannel(channel)
//            builder = Notification.Builder(this, Mychannel)
//        }else{
//            builder = Notification.Builder(this)
//        }

//        val intent = Intent(this, MainActivity::class.java)
//        val pendingIntent=PendingIntent.getActivity(this,1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        val notification = builder.setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("音乐")
//            .setContentText("this is a music")
//            .setContentIntent(pendingIntent)
//            .build()
//
//        startForeground(1, notification)

//        mediaPlayer.setOnPreparedListener {
//            it.start()
//            val notification2= builder.setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle("音乐")
//            .setContentText(musicNameList.get(current))
//            .setContentIntent(pendingIntent)
//            .build()
//
//            notificationManager.notify(1, notification2)
//            val intent2 = Intent(MyReceiverAction)
//            intent2.putExtra("operate", 1)
//            intent2.setPackage(packageName)
//            sendBroadcast(intent2)
//        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val operate = intent?.getIntExtra(Commond,1)?:1

        when(operate){
            1->play()
            2->pause()
            3->stop()
            4->next()
            5->prev()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    fun pause(){
        if(isPause){
            mediaPlayer.start()
            isPause = false
        }
        else{
            mediaPlayer.pause()
            isPause = true
        }
    }
    fun stop(){
        mediaPlayer.stop()
        stopSelf()
    }
    fun next(){
        current++
        if(current>=musicList.size){
            current = 0
        }
        play()
    }
    fun prev(){
        current--
        if(current< 0 ){
            current = musicList.size -1
        }
        play()
    }

    fun play(){
        if(musicList.size == 0) return
        val path = musicList[current]
        mediaPlayer.reset()
        try{
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    override fun onBind(intent: Intent): IBinder {
       return binder
    }

    private fun getMusicList(){
        val cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null)
        if(cursor!=null){
            while (cursor.moveToNext()){
                val musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicList.add(musicPath)
                val musicName =cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList.add(musicName)
                Log.d(TAG,"getMusicList:$musicPath name:$musicName")
            }
            cursor.close()
        }
    }
}
