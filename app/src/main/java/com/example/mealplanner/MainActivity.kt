package com.example.mealplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Logbtn.setOnClickListener(){
            val intentLog = Intent(this, LogActivity::class.java)
            startActivity(intentLog)
        }
        Libbtn.setOnClickListener(){
            val intentLib = Intent(this, LibActivity::class.java)
            startActivity(intentLib)
        }
        Bkmbtn.setOnClickListener(){
            val intentBkm = Intent(this, BkmActivity::class.java)
            startActivity(intentBkm)
        }
    }
}