package com.androrubin.recruitmentApp

import com.androrubin.recruitmentApp.EditRecruiterProfile
import com.androrubin.recruitmentApp.Login_Activity
import com.androrubin.recruitmentApp.R


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.FragmentManager
import com.androrubin.recruitmentApp.RecruiterMainPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RecruiterProfileFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {


        return inflater.inflate(R.layout.fragment_recruiter_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db= FirebaseFirestore.getInstance()
        mAuth= FirebaseAuth.getInstance()
        val currentuser=mAuth.currentUser
        val name=currentuser?.displayName



        val cbhealth= getView()?.findViewById<CheckBox>(R.id.cbhealth)
        val cbconsult =(getView()?.findViewById<CheckBox>(R.id.cbconsult))
        val cbedu=getView()?.findViewById<CheckBox>(R.id.cbedu)
        val cbtypist=getView()?.findViewById<CheckBox>(R.id.cbtypist)
        val cbsoftware=getView()?.findViewById<CheckBox>(R.id.cbsoft)
        val compname=getView()?.findViewById<TextView>(R.id.tvcompanyname)
        val compaddress=getView()?.findViewById<TextView>(R.id.tv_address)
        val compcontact=getView()?.findViewById<TextView>(R.id.tv_mobileno)
        val compemail=getView()?.findViewById<TextView>(R.id.tv_email)
        val logout=getView()?.findViewById<Button>(R.id.logout_btn)
        val edit=getView()?.findViewById<ImageButton>(R.id.btn_edit)

        logout?.setOnClickListener{
        mAuth.signOut()
            Toast.makeText(context,"User logged out ", Toast.LENGTH_SHORT).show()
            val intent=Intent(this@RecruiterProfileFragment.requireContext(),Login_Activity::class.java)
            startActivity(intent)
        }
            db.collection("Recruiter").document("$name")
                .get()
                .addOnSuccessListener {
                    var name=it["company name"].toString()
                    var address=it["company address"].toString()
                    var email=it["company email"].toString()
                    var contact=it["company contact"].toString()
                    cbhealth?.isChecked= it["cbhealth"] as Boolean
                    cbconsult?.isChecked=it["cbconsult"] as Boolean
                    cbedu?.isChecked=it["cbedu"] as Boolean
                    cbtypist?.isChecked=it["cbtypist"] as Boolean
                    cbsoftware?.isChecked=it["cbsoftware"] as Boolean

                    compaddress?.text=address
                    compemail?.text=email
                    compcontact?.text=contact
                    compname?.text=name
                }
            edit?.setOnClickListener{
                val intent=Intent(this@RecruiterProfileFragment.requireContext(),EditRecruiterProfile::class.java)
                startActivity(intent)
            }
        }
    }


