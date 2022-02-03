package com.androrubin.recruitmentApp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_recruiter_mainpage.*

class RecruiterMainPage:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruiter_mainpage)
        val btnprofile = findViewById<Chip>(R.id.btn_profilepage)

        btn_postView.setOnClickListener {
            Intent(this, Vacancy_details::class.java).also {
                startActivity(it)
            }
        }
        btnprofile.setOnClickListener {
            Intent(this, RecruiterProfile::class.java).also {
                startActivity(it)
            }
        }

        btn_jobcreate.setOnClickListener {
            Intent(this, Create_vacancy::class.java).also {
                startActivity(it)
            }
        }
    }
}