 package com.androrubin.recruitmentApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

 class SplashScreen : AppCompatActivity() {

     private lateinit var mAuth: FirebaseAuth
     private lateinit var db: FirebaseFirestore

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_splash)
         supportActionBar?.hide()

         mAuth = FirebaseAuth.getInstance()
         db = FirebaseFirestore.getInstance()

         val user = mAuth.currentUser
         val name = user?.displayName

             Handler().postDelayed({
                 if (user != null) {

                     db.collection("Users").document("$name")
                         .get()
                         .addOnSuccessListener {

                             //Returns value of corresponding field
                             var a = it["Role"].toString()

                             if (a== "Recruiter") {


                                 val dashboardIntent = Intent(this, RecruiterDetail::class.java)
                                 startActivity(dashboardIntent)
                                 finish()

                             }else if (a== "Job Seeker") {

                                 val dashboardIntent = Intent(this,SeekerDetail::class.java)
                                 startActivity(dashboardIntent)
                                 finish()

                             } else {

                                 val dashboardIntent = Intent(this, RoleSelect::class.java)
                                 startActivity(dashboardIntent)
                                 finish()

                             }
                         }

                 } else {
                     val signInIntent = Intent(this, Login_Activity::class.java)
                     startActivity(signInIntent)
                     finish()
                 }
             }, 2000)
     }

 }