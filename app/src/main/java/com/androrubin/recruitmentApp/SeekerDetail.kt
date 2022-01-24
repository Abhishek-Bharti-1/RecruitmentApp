package com.androrubin.recruitmentApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SeekerDetail : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_detail)


        mAuth = FirebaseAuth.getInstance()
        val logout = findViewById<Button>(R.id.logout_btn)

        logout.setOnClickListener {
            Toast.makeText(applicationContext,"User logged out ", Toast.LENGTH_SHORT).show()
            mAuth.signOut()
            val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
            finish()
        }
    }
}