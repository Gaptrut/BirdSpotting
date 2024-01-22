package com.example.birds

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var emailView: EditText
    lateinit var passwordView: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = Firebase.auth
        emailView = findViewById(R.id.emailEditText)
        passwordView = findViewById(R.id.passwordEditText)

        val signupButton = findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            signUp()

        }

        val signInButton = findViewById<Button>(R.id.signinButton)
        signInButton.setOnClickListener {
            signIn()
        }

    }


    fun goToDashboardActivity() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
    }


    fun signIn(){
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.d("!!!", "Signed in")
                    goToDashboardActivity()
                } else {
                    Log.d("!!!", "User not signed in ${task.exception}")
                }

            }
    }

    fun signUp() {
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if (email.isEmpty() || password.isEmpty()){

            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    goToDashboardActivity()
                    Log.d("!!!", "Create success")

                }else{
                    Log.d("!!!", "User not created ${task.exception}")
                }

            }



    }
}









