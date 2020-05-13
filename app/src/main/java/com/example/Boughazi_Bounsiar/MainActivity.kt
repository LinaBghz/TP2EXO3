package com.example.Boughazi_Bounsiar

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            var intent : Intent = Intent(this, ReminderService::class.java)
            intent.putExtra("Time", time1.text.toString())
            intent.putExtra("Prayer", prayer1.text.toString())
            startService(intent)
        }

        stop_button.setOnClickListener {
            stopService(Intent(this, ReminderService::class.java))
        }

    }
}
