package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.content.BroadcastReceiver
import android.widget.RadioButton
import android.widget.RadioGroup


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private var stringUrl: String = ""
   private val NOTIFICATION_ID=0
 private lateinit var notificationManager: NotificationManager
   // private lateinit var downloadManager: DownloadManager
   // private lateinit var receiver: BroadcastReceiver

    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager

        custom_button.setOnClickListener {

            when (radiogroup.checkedRadioButtonId) {
                R.id.glide -> {
                    stringUrl = GLIDE_URL
                    fileName = getString(R.string.glide_image_loading_library_by_bumptech)
                    download()
                    custom_button.buttonState = ButtonState.Loading
                }
                R.id.retrofit -> {
                    stringUrl = RETROFIT_URL
                    fileName =
                        getString(R.string.retrofit_a_type_safe_http_client_for_android_and_java_by_square_inc)
                    download()
                    custom_button.buttonState = ButtonState.Loading
                }
                R.id.starter -> {
                    stringUrl = LOAD_APP_URL
                    fileName = getString(R.string.loadapp_current_repository_by_udacity)
                    download()
                    custom_button.buttonState = ButtonState.Loading
                }

                else ->
                    Toast.makeText(this, "Select File To Download", Toast.LENGTH_SHORT).show()


            }
        }
    }
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            createDownloadNotificationChannel(this@MainActivity)

            if (id == downloadID) {
                fileStatus = ""
                Toast.makeText(
                    context,
                    getString(R.string.download_completed),
                    Toast.LENGTH_SHORT
                ).show()

                val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val cursor: Cursor =
                    downloadManager.query(DownloadManager.Query().setFilterById(downloadID))
                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(
                        cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    )

                    when (status) {
                        DownloadManager.STATUS_FAILED -> {
                            fileStatus = "Failed"
                            custom_button.buttonState = ButtonState.Completed
                        }
                        DownloadManager.STATUS_SUCCESSFUL -> {
                            fileStatus = "Success"
                            custom_button.buttonState = ButtonState.Completed

                            notificationManager.sendNotification(
                                stringUrl,
                                fileStatus,
                                this@MainActivity
                            )
                        }
                    }
                }
            }
        }
    }





            private fun download() {
                val request =
                    DownloadManager.Request(Uri.parse(stringUrl))
                        .setTitle(fileName)
                        .setDescription(getString(R.string.app_description))
                        .setRequiresCharging(false)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)

                val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                downloadID =
                    downloadManager.enqueue(request)// enqueue puts the download request in the queue.
            }


    companion object {
        //private const val URL =
        //   "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"

        private const val LOAD_APP_URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val GLIDE_URL = "https://github.com/bumptech/glide/archive/master.zip"
        private const val RETROFIT_URL = "https://github.com/square/retrofit/archive/master.zip"

        lateinit var fileName: String
        lateinit var fileStatus: String


    }




}
