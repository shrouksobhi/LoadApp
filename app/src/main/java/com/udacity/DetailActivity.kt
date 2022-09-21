package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {
  // private var fileName:TextView=findViewById(R.id.file_name)
  //  private var statusName:TextView=findViewById(R.id.status_name)
//    private var ok: Button =findViewById(R.id.ok)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

//        var name=intent.getStringExtra("NAME")
//        var status=intent.getStringExtra("STATUS")

            file_name.text = intent.getStringExtra("title")
            status.text = intent.getStringExtra("status")
            ok.setOnClickListener {

                finish()

            }
      val notificationManager = ContextCompat.getSystemService(applicationContext,
          NotificationManager::class.java)
      //Cancels the Notification when we press "See File"
      notificationManager?.cancelNotifications()



  }

}
