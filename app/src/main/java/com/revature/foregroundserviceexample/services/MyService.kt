package com.revature.foregroundserviceexample.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.revature.foregroundserviceexample.R

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

     val command= intent?.getStringExtra(INTENT_COMMAND)
       if(command=="Stop")
       {
           stopService()
           return START_NOT_STICKY
       }

        showNotification()

        if(command=="Reply")
        {
            Toast.makeText(this,"CLicked in the NOtification",Toast.LENGTH_LONG).show()
        }

        return START_NOT_STICKY
        //return super.onStartCommand(intent, flags, startId)
    }



    private fun stopService()
    {
        stopForeground(true)

        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showNotification()
    {
        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val replyIntent=Intent(this,MyService::class.java).apply {

            putExtra(INTENT_COMMAND, "Reply")
        }
        val replyPendingIntent=PendingIntent.getService(this,2,replyIntent,0)

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
                   // this.startForeground(1,build())
                    manager.createNotificationChannel(this)

                }

            }
            catch(e:Exception)
            {
                Log.d("Error displaying ","Show Notification ${e.localizedMessage}")
            }
        }

        with(

            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_GENERAL)
        )
        {

            setContentTitle("First")
            setContentText("Notification Text")
            setSmallIcon(R.drawable.ic_launcher_background)
            setContentIntent(replyPendingIntent)
            addAction(0,"Reply",replyPendingIntent)
            addAction(0,"Answer",replyPendingIntent)
            startForeground(1,build())
        }


    }


}