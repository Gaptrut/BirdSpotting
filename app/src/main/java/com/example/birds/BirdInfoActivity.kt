package com.example.birds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BirdInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bird_info)

        val birdId = intent.getStringExtra("birdId")

        val backButton = findViewById<Button>(R.id.infoBackButton)
        backButton.setOnClickListener {
            finish()
        }
    }
}
