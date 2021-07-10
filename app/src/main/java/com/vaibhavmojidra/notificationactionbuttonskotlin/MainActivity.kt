package com.vaibhavmojidra.notificationactionbuttonskotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.vaibhavmojidra.notificationactionbuttonskotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var manager:NotificationManagerCompat
    private val CHANNEL_ID="MESSAGE"
    private val CHANNEL_NAME="MESSAGE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.showNotification.setOnClickListener {
            showNotification()
        }
    }

    private fun showNotification() {
        manager= NotificationManagerCompat.from(this)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationChannel=NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(notificationChannel)
        }
        //For Info Action Button
        val pendingInfoActivityIntent=PendingIntent.getActivity(this,0,Intent(this@MainActivity,InfoActivity::class.java),PendingIntent.FLAG_UPDATE_CURRENT)
        val infoActivityAction=NotificationCompat.Action.Builder(0,"Info",pendingInfoActivityIntent).build()

        //For Settings Action Button
        val pendingSettingsActivityIntent=PendingIntent.getActivity(this,0,Intent(this@MainActivity,SettingsActivity::class.java),PendingIntent.FLAG_UPDATE_CURRENT)
        val settingsActivityAction=NotificationCompat.Action.Builder(0,"Settings",pendingSettingsActivityIntent).build()

        val notification= NotificationCompat.Builder(this@MainActivity, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .setContentTitle("Notification Title")
                .setContentText("Notification Content Text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(infoActivityAction)// Setting Info Action
                .addAction(settingsActivityAction)// Setting Settings Action
                .build()
        manager.notify(0, notification)
    }
}