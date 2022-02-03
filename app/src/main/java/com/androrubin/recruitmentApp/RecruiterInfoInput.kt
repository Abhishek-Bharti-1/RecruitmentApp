package com.androrubin.recruitmentApp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RecruiterInfoInput: AppCompatActivity() {

    private lateinit var db:FirebaseFirestore
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruiter_detailfill)
        db= FirebaseFirestore.getInstance()
        mAuth= FirebaseAuth.getInstance()
        val currentUser=mAuth.currentUser


        val recruitername=findViewById<EditText>(R.id.et_name_recruiter)
        val recruiteraddress=findViewById<EditText>(R.id.et_address_recruiter)
        val recruiteremail=findViewById<EditText>(R.id.et_email_recruiter)
        val recruitercontactno=findViewById<EditText>(R.id.et_contactno_recruiter)


        val name = currentUser?.displayName
        val save=findViewById<Button>(R.id.btn_saveinfo)

        save?.setOnClickListener {
            val cbhealth=(findViewById<CheckBox>(R.id.cb_health)).isChecked
            val cbconsult =(findViewById<CheckBox>(R.id.cb_consultancy)).isChecked
            val cbedu=(findViewById<CheckBox>(R.id.cb_edu)).isChecked
            val cbtypist=(findViewById<CheckBox>(R.id.cb_typist)).isChecked
            val cbsoftware=findViewById<CheckBox>(R.id.cb_software).isChecked
            if((TextUtils.isEmpty(recruitername.text.trim().toString()))&&(TextUtils.isEmpty(recruiteraddress.text.trim().toString()))&&(TextUtils.isEmpty(recruiteremail.text.trim().toString()))&&TextUtils.isEmpty(recruitercontactno.text.trim().toString())) {
                Toast.makeText(this,"No Field Can be Empty",Toast.LENGTH_SHORT).show()
            }
            else{
                val data= hashMapOf(
                    "company name" to recruitername.text.toString(),
                    "company address" to recruiteraddress.text.toString(),
                    "company email" to recruiteremail.text.toString(),
                    "company contact" to recruitercontactno.text.toString(),
                    "cbconsult" to cbconsult,
                    "cbhealth" to cbhealth,
                    "cbedu" to cbedu,
                    "cbtypist" to cbtypist,
                    "cbsoftware" to cbsoftware
                )
                db= FirebaseFirestore.getInstance()
                db.collection("Recruiter").document("$name")
                    .set(data)
                    .addOnSuccessListener { docRef ->
                        Log.d("Data Addition","DocumentSnapshot written with ID: ${docRef}.id}")

                    }
                    .addOnFailureListener { e ->
                        Log.w("Data Addition", "Error adding document", e)
                    }
                db.collection("Users").document("$name")
                    .update(
                        mapOf(
                            "ProfileCreated" to "1"
                        )
                    )
                    .addOnSuccessListener {
                        Log.d("Data Addition", "DocumentSnapshot written with ID: ${it}.id")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Data Addition", "Error adding document", e)
                    }
                val intent=Intent(this,RecruiterMainPage::class.java)
                Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }

        }
    }

}