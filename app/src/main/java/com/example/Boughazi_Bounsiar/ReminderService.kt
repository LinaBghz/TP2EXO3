package com.example.Boughazi_Bounsiar



import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.Boughazi_Bounsiar.R.*
import java.util.*

class ReminderService:Service(){

    private var player: MediaPlayer? = null
    private val timer: Timer = Timer()
    private var prayer : String = ""
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmTime = intent!!.getStringExtra("Time")
        prayer = intent!!.getStringExtra("Prayer")
        player = MediaPlayer.create(this, raw.adhan)
        player!!.isLooping = true

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pIntent = PendingIntent.getService(this, System.currentTimeMillis().toInt(), intent!!, 0)

        val builder = builder(notificationManager, pIntent)
        var notif = builder!!.build()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val currentTime = Calendar.getInstance().time.hours.toString() + ":" +  Calendar.getInstance().time.minutes.toString()
                if (currentTime==alarmTime){
                    startForeground(1, notif)
                    player!!.start()
                } else { }
            }
        }, 0, 2000)


        return START_STICKY
    }

    private fun builder(nm: NotificationManager, pi: PendingIntent): Notification.Builder? {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                "ch00", "ch00", NotificationManager.IMPORTANCE_HIGH)
            nm.createNotificationChannel(mChannel)
            val builder = Notification.Builder(this,"ch00")
                .setContentTitle("Prayer Reminder")
                .setContentText("It's time to pray "+prayer).setSmallIcon(drawable.prayer)
                .setContentIntent(pi).setAutoCancel(true)
                .setAutoCancel(true)
            return builder


        }else{

            val builder = Notification.Builder(this)
                .setContentTitle("Prayer Reminder")
                .setContentText("Time to pray" + prayer).setSmallIcon(drawable.prayer)
                .setContentIntent(pi).setAutoCancel(true)
                .setOngoing(true)
                .setAutoCancel(true)
            return builder

        }

    }

    override fun onBind(intent: Intent?): IBinder? { return null }
    override fun onDestroy() {
        super.onDestroy()
        player!!.stop()
    }


}