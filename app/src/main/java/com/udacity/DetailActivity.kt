package com.udacity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
   private var fileName:TextView=findViewById(R.id.file_name)
    private var statusName:TextView=findViewById(R.id.status_name)
    private var ok: Button =findViewById(R.id.ok)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        var name=intent.getStringExtra("NAME")
        var status=intent.getStringExtra("STATUS")
        fileName.text=name.toString()
        statusName.text=status.toString()
        ok.setOnClickListener{}
        val intent= Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()




    }

}
