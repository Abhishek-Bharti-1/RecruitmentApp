package com.androrubin.recruitmentApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_seeker_create_profile.*

class SeekerCreateProfile : AppCompatActivity() {

    private lateinit var db:FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_create_profile)

        db= FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val name = currentUser?.displayName

        btnSave.setOnClickListener {

            if (TextUtils.isEmpty(edtName.getText()?.trim().toString())) {
                edtName.setError("Title cannot be empty")
                edtName.requestFocus()
            }else  if (TextUtils.isEmpty(edtPhone.getText()?.trim().toString())) {
                edtPhone.setError("Description cannot be empty")
                edtPhone.requestFocus()
            }
            else if (TextUtils.isEmpty(edtEmail.getText()?.trim().toString())) {
                edtEmail.setError("Title cannot be empty")
                edtEmail.requestFocus()
            }else  if (TextUtils.isEmpty(edtAddress.getText()?.trim().toString())) {
                edtAddress.setError("Description cannot be empty")
                edtAddress.requestFocus()
            }
            else  if (TextUtils.isEmpty(edtQualifications.getText()?.trim().toString())) {
                edtQualifications.setError("Description cannot be empty")
                edtQualifications.requestFocus()
            }else{

            val data= hashMapOf(
                "Name" to edtName.text.trim().toString(),
                "Phone Number" to edtPhone.text.trim().toString(),
                "Email Id" to edtEmail.text.trim().toString(),
                "Address" to edtAddress.text.trim().toString(),
                "Qualification" to edtQualifications.text.trim().toString(),
                "Job Preference" to ""
            )
            db.collection("Seekers").document("$name")
                .set(data)
                .addOnSuccessListener {docRef ->
                    Log.d("Data Addition", "DocumentSnapshot written with ID: ${docRef}.id")
                    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
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
            startActivity(Intent(this,SeekerMainActivity::class.java))
            finish()
            }
        }
    }
}