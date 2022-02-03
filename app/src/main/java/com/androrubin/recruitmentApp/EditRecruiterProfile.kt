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

class EditRecruiterProfile : AppCompatActivity() {
    private lateinit var db:FirebaseFirestore
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recruiter_profile)

        //Instancing of database
        db=FirebaseFirestore.getInstance()
        mAuth= FirebaseAuth.getInstance()
        val currentUser=mAuth.currentUser


        //Getting TextViews from Profile Page
        val compname=findViewById<EditText>(R.id.et_name_edit)
        compname.setText(intent.getStringExtra("Extra_Name"))
        val compaddress=findViewById<EditText>(R.id.et_address_edit)
        compaddress.setText(intent.getStringExtra("Extra_Address"))
        val compemail=findViewById<EditText>(R.id.et_email_edit)
        compemail.setText(intent.getStringExtra("Extra_Email"))
        val compcontact=findViewById<EditText>(R.id.et_contactno_edit)
        compcontact.setText(intent.getStringExtra("Extra_Contact"))

        //Getting Checkbox from Profile Page
        val cbhealth=findViewById<CheckBox>(R.id.cbhealth)
        cbhealth.isChecked=intent.getBooleanExtra("Extra_Health",false)
        val cbedu=findViewById<CheckBox>(R.id.cbedu)
        cbedu.isChecked=intent.getBooleanExtra("Extra_Education",false)
        val cbconsult=findViewById<CheckBox>(R.id.cbconsult)
        cbconsult.isChecked=intent.getBooleanExtra("Extra_Consultance",false)
        val cbtypist=findViewById<CheckBox>(R.id.cbtypist)
        cbtypist.isChecked=intent.getBooleanExtra("Extra_Typist",false)
        val cbsoft=findViewById<CheckBox>(R.id.cbsoft)
        cbsoft.isChecked=intent.getBooleanExtra("Extra_Software",false)

        //Saving edited data to Firebase
        val name=currentUser?.displayName
        val save=findViewById<Button>(R.id.btn_save_edit)

        save?.setOnClickListener{
            val cbhealth=cbhealth.isChecked
            val cbconsult =cbconsult.isChecked
            val cbedu=cbedu.isChecked
            val cbtypist=cbtypist.isChecked
            val cbsoftware=cbsoft.isChecked

            if((TextUtils.isEmpty(compname.text.trim().toString()))&&(TextUtils.isEmpty(compaddress.text.trim().toString()))&&(TextUtils.isEmpty(compcontact.text.trim().toString()))&& (TextUtils.isEmpty(compemail.text.trim().toString()))) {
                Toast.makeText(this,"No Field Can be Empty", Toast.LENGTH_SHORT).show()
            }
            else{
                val data= hashMapOf(
                    "company name" to compname.text.toString(),
                    "company address" to compaddress.text.toString(),
                    "company email" to compemail.text.toString(),
                    "company contact" to compcontact.text.toString(),
                    "cbconsult" to cbconsult,
                    "cbhealth" to cbhealth,
                    "cbedu" to cbedu,
                    "cbtypist" to cbtypist,
                    "cbsoftware" to cbsoftware
                )
                db.collection("Recruiter").document("$name")
                    .set(data)
                    .addOnSuccessListener { docRef ->
                        Log.d("Data Addition","DocumentSnapshot written with ID: ${docRef}.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Data Addition", "Error adding document", e)
                    }
                Intent(this,RecruiterProfile::class.java).also {
                    Toast.makeText(this,"Profile Updated",Toast.LENGTH_SHORT).show()
                    startActivity(it)
                    finish()
                }
            }
        }

    }
}