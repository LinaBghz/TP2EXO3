package com.example.Boughazi_Bounsiar


import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.Boughazi_Bounsiar.R.*
import java.util.*

class ReminderService:Service(){

    private var player: MediaPlayer? = null
    private val timer: Timer = Timer()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmTime = intent!!.getStringExtra("Time")
        val prayer = intent!!.getStringExtra("Prayer")
        player = MediaPlayer.create(this, raw.adhan)
        player!!.isLooping = true

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notif : Notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                "ch006", "ch006", NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(mChannel)
           notif = NotificationCompat.Builder(this, "ch006")
                .setSmallIcon(drawable.ic_launcher_background)
                .setContentTitle("Reminder for prayer")
                .setContentText("Time to pray" + prayer)
                .setContentIntent(pendingIntent)
                .build()
            notificationManager.notify(1, notif)
        }
        else {
          notif = Notification.Builder(this)
                .setContentTitle("Prayer")
                .setContentTitle("Reminder for prayer")
                .setContentText("Time to pray" + prayer).setSmallIcon(drawable.ic_launcher_background)
                .setContentIntent(pendingIntent).setAutoCancel(true)
                .setOngoing(true)
                .build()
            notificationManager.notify(1, notif)
        }
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val currentTime = Calendar.getInstance().time.hours.toString() + ":" +  Calendar.getInstance().time.minutes.toString()
                    if (currentTime==alarmTime){
                        startForeground(1, notif)
                        notificationManager.notify(1,notif)
                        player!!.start()
                    } else { }
                }
            }, 0, 2000)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? { return null }
    override fun onDestroy() {
        super.onDestroy()
        player!!.stop()
    }

}