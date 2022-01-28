package com.androrubin.recruitmentApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RoleSelect : AppCompatActivity() {

    private lateinit var db:FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_select)

        db= FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        val seekerBtn= findViewById<Button>(R.id.seekerBtn)
        val recruiterBtn= findViewById<Button>(R.id.recruiterBtn)
        val name = currentUser?.displayName

        seekerBtn.setOnClickListener {

            val data = hashMapOf(
                "Role" to "Job Seeker",
                "LinkProfilePic" to "",
                "LinkBannerPic" to ""
            )
            db.collection("Users").document("$name")
                .set(data)
                .addOnSuccessListener {docRef ->
                    Log.d("Data Addition", "DocumentSnapshot written with ID: ${docRef}.id")
                }
                .addOnFailureListener { e ->
                    Log.w("Data Addition", "Error adding document", e)
                }
            startActivity(Intent(this,SeekerDetail::class.java))
            finish()

        }
        recruiterBtn.setOnClickListener {

            val data = hashMapOf(
                "Role" to "Recruiter"
            )
            db.collection("Users").document("$name")
                .set(data)
                .addOnSuccessListener {docRef ->
                    Log.d("Data Addition", "DocumentSnapshot written with ID: ${docRef}.id")
                }
                .addOnFailureListener { e ->
                    Log.w("Data Addition", "Error adding document", e)
                }
            startActivity(Intent(this,RecruiterDetail::class.java))
            finish()
        }
    }
}