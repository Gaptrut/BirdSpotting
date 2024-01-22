package com.example.birds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        Log.d("DashboardActivity", "DashboardActivity started!")

        val mapButton = findViewById<Button>(R.id.mapButton)
        mapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        val recyclerViewButton = findViewById<Button>(R.id.recyclerViewButton)
        recyclerViewButton.setOnClickListener {
            val intent = Intent(this, BirdListActivity::class.java)
            startActivity(intent)
        }


    }
}


