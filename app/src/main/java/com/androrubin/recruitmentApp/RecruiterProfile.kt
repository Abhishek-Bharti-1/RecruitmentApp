package com.androrubin.recruitmentApp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RecruiterProfile:AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruiter_profile)



        db= FirebaseFirestore.getInstance()
        mAuth= FirebaseAuth.getInstance()
        val currentuser=mAuth.currentUser
        val name=currentuser?.displayName

        val cbhealth=findViewById<CheckBox>(R.id.cbhealth)
        val cbconsult =(findViewById<CheckBox>(R.id.cbconsult))
        val cbedu=findViewById<CheckBox>(R.id.cbedu)
        val cbtypist=findViewById<CheckBox>(R.id.cbtypist)
        val cbsoftware=findViewById<CheckBox>(R.id.cbsoft)
        val compname=findViewById<TextView>(R.id.tvcompanyname)
        val compaddress=findViewById<TextView>(R.id.tv_address)
        val compcontact=findViewById<TextView>(R.id.tv_mobileno)
        val compemail=findViewById<TextView>(R.id.tv_email)
        val logout=findViewById<Button>(R.id.logout_btn)
        val edit=findViewById<ImageButton>(R.id.btn_edit)
        logout.setOnClickListener{
            Toast.makeText(applicationContext,"User logged out ", Toast.LENGTH_SHORT).show()
            mAuth.signOut()
            val intent= Intent(this,Login_Activity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        db.collection("Recruiter").document("$name")
            .get()
            .addOnSuccessListener {
                var name=it["company name"].toString()
                var address=it["company address"].toString()
                var email=it["company email"].toString()
                var contact=it["company contact"].toString()
                cbhealth.isChecked= it["cbhealth"] as Boolean
                cbconsult.isChecked=it["cbconsult"] as Boolean
                cbedu.isChecked=it["cbedu"] as Boolean
                cbtypist.isChecked=it["cbtypist"] as Boolean
                cbsoftware.isChecked=it["cbsoftware"] as Boolean

                compaddress.text=address
                compemail.text=email
                compcontact.text=contact
                compname.text=name


            }
        edit.setOnClickListener{
            Intent(this,EditRecruiterProfile::class.java).also {
                it.putExtra("Extra_Name",compname.text.toString())
                it.putExtra("Extra_Address",compaddress.text.toString())
                it.putExtra("Extra_Email",compemail.text.toString())
                it.putExtra("Extra_Contact",compcontact.text.toString())
                it.putExtra("Extra_Health",cbhealth.isChecked)
                it.putExtra("Extra_Typist",cbtypist.isChecked)
                it.putExtra("Extra_Education",cbedu.isChecked)
                it.putExtra("Extra_Consultance",cbconsult.isChecked)
                it.putExtra("Extra_Software",cbsoftware.isChecked)
                startActivity(it)
                finish()
            }
        }
}
}