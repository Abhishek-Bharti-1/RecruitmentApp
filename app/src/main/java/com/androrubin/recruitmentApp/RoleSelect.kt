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
                "LinkProfilePic" to null,
                "LinkBannerPic" to null,
                "ProfileCreated" to 0
            )
            db.collection("Users").document("$name")
                .set(data)
                .addOnSuccessListener {docRef ->
                    Log.d("Data Addition", "DocumentSnapshot written with ID: ${docRef}.id")
                }
                .addOnFailureListener { e ->
                    Log.w("Data Addition", "Error adding document", e)
                }
            db.collection("Users").document("$name")
                .get()
                .addOnSuccessListener {

                    //Returns value of corresponding field
                    val b = it["ProfileCreated"].toString()
                    if(b=="1"){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else{
                        startActivity(Intent(this, SeekerCreateProfile::class.java))
                        finish()
                    }

                }
        }
        recruiterBtn.setOnClickListener {

            val data = hashMapOf(
                "Role" to "Recruiter",
                "LinkProfilePic" to null,
                "LinkBannerPic" to null,
                "ProfileCreated" to 0
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