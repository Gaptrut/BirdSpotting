package com.example.birds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class AddItemActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var nameView: EditText
    lateinit var placeView: EditText
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        auth = Firebase.auth

        db = Firebase.firestore

        nameView = findViewById(R.id.birdEditText)
        placeView = findViewById(R.id.placeEditText)


        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            saveItem()
        }
    }


    fun saveItem() {
        val item = Item(
            birdname = nameView.text.toString(),
            place = placeView.text.toString()


        )

        nameView.setText("")
        placeView.setText("")

        val user = auth.currentUser
        if (user == null) {
            return
        }

        db.collection("user").document(user.uid)
            .collection("items").add(item)
            .addOnSuccessListener {
                db.collection("AllBirds").add(item)
            }
            .addOnFailureListener {
                // hantera eventuella fel här
            }


                LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(Intent("BIRD_ADDED"))

                val intent = Intent(this, BirdListActivity::class.java)
                intent.putExtra("userId", user.uid)
                startActivity(intent)
    }
}

        //annotation
        data class Item(
            @DocumentId var documentId: String? = null,
            var birdname: String? = null,
            var place: String? = null,
            var userID: String? = null

        )






