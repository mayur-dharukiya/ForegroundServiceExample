package com.revature.foregroundserviceexample.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log

//foreground service

const val NOTIFICATION_CHANNEL_GENERAL="Checking"
const val INTENT_COMMAND="Command"

class MyService: Service()
{
    override fun onBind(p0: Intent?): IBinder?=null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        showNotification()
        //return super.onStartCommand(intent, flags, startId)
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
    }

    private fun stopService()
    {
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showNotification()
    {
        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {

            try {



                with(

                    NotificationChannel(

                        NOTIFICATION_CHANNEL_GENERAL,
                        "Hello Mayur",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
                {
                    enableLights(true)
                    setShowBadge(true)
                    enableVibration(true)
                    setSound(null,null)
                    setDescription("Hello Description")

                    lockscreenVisibility= Notification.VISIBILITY_PRIVATE
                    manager.createNotificationChannel(this)

                }

            }
            catch(e:Exception)
            {
                Log.d("Error displaying ","Show Notification ${e.localizedMessage}")
            }
        }

        //use the alternative code to create notification old way



    }


}