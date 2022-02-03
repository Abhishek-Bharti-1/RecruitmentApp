package com.androrubin.recruitmentApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Create_vacancy : AppCompatActivity() {

    // private lateinit var db : FirebaseFirestore
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_vacancy)

        supportActionBar?.hide()

        val post_lay= findViewById<TextInputLayout>(R.id.post1)
        val Job_des= findViewById<TextInputLayout>(R.id.J_des)

        val post= findViewById<TextInputEditText>(R.id.post_text1)

        post.doOnTextChanged { text, start, before, count ->
            if(text!!.length > 30){
                post_lay.error="describe the post in description!"
            } else if(text.length<30){
                post_lay.error= null
            }
        }
        val company_name= findViewById<TextInputEditText>(R.id.c_name)
        val salary_range = findViewById<TextInputEditText>(R.id.salary_range)
        val job_des = findViewById<TextInputEditText>(R.id.job_des)
        job_des.doOnTextChanged { text, start, before, count ->
            if(text!!.length > 150){
                post_lay.error="describe the post in description!"
            } else if(text.length<150){
                post_lay.error= null
            }
        }

        val working_hrs = findViewById<TextInputEditText>(R.id.wrk_time)
        val min_qual = findViewById<TextInputEditText>(R.id.Min_qual)
        val experience = findViewById<TextInputEditText>(R.id.exp_txt)
        val skills = findViewById<TextInputEditText>(R.id.skill_txt)
        val address =findViewById<TextInputEditText>(R.id.comadr_txt)

        var name = "Vacancy des"

        val submit = findViewById<Button>(R.id.sub_btn1)
        submit?.setOnClickListener {

//          Toast.makeText(this,title.getText().toString(),Toast.LENGTH_SHORT).show()
            if(TextUtils.isEmpty(post.getText()?.trim().toString())) {
                post.setError("Post cannot be empty")
                post.requestFocus()
            }else if (TextUtils.isEmpty(company_name.getText()?.trim().toString())){
                company_name.setError(" cannot be empty")
                company_name.requestFocus()
            }else if (TextUtils.isEmpty(working_hrs.text?.trim().toString())){
                company_name.setError(" cannot be empty")
                company_name.requestFocus()
            }else if (TextUtils.isEmpty(min_qual.getText()?.trim().toString())){
                company_name.setError(" cannot be empty")
                company_name.requestFocus()
            }else if (TextUtils.isEmpty(address.getText()?.trim().toString())){
                company_name.setError(" cannot be empty")
                company_name.requestFocus()
            }
            else{

                val vac_detail = Details(
                    company_name.text.toString(),
                    post.text.toString(),
                    salary_range.text.toString(),
                    job_des.text.toString(),
                    working_hrs.text.toString(),
                    min_qual.text.toString(),
                    experience.text.toString(),
                    skills.text.toString(),
                    address.text.toString()
                )

                db = FirebaseDatabase.getInstance().getReference("Detail")
                db.child(post.text.toString()).setValue(vac_detail).addOnSuccessListener {

                    company_name.text?.clear()
                    post.text?.clear()
                    salary_range.text?.clear()
                    job_des.text?.clear()
                    working_hrs.text?.clear()
                    min_qual.text?.clear()
                    experience.text?.clear()
                    skills.text?.clear()
                    address.text?.clear()


                    Toast.makeText(applicationContext, "added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this,RecruiterMainPage::class.java)
                Toast.makeText(applicationContext,"Post Added", Toast.LENGTH_SHORT).show()
                //Toast.makeText(this,"Post Added", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
        }
    }
}