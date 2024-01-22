package com.example.birds

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class BirdListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BirdRecyclerAdapter

    private val birdAddedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateBirds()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycleview)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = FirebaseFirestore.getInstance()

        db.collection("AllBirds")
            .addSnapshotListener { snapshot, e ->


                val birds = snapshot?.toObjects(Bird::class.java)
                Toast.makeText(this, "Got ${birds?.size} birds from Firebase", Toast.LENGTH_LONG)
                    .show()

                val mutableBirds = birds?.toMutableList()
                adapter = BirdRecyclerAdapter(this, mutableBirds ?: mutableListOf())
                recyclerView.adapter = adapter
            }

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        val mapButton = findViewById<Button>(R.id.kartaButton)
        mapButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

    }



    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(birdAddedReceiver, IntentFilter("BIRD_ADDED"))
    }

    override fun onPause() {
        super.onPause()

        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(birdAddedReceiver)
    }

    fun updateBirds() {
        val db = FirebaseFirestore.getInstance()

        db.collection("user").get()
            .addOnSuccessListener { result ->
                val user = result.map { it.toObject(Bird::class.java) }.toMutableList()
                adapter.updateBirds(user)

            }
    }




}









