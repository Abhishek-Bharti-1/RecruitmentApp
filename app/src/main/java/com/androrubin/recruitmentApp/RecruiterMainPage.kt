package com.androrubin.recruitmentApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_recruiter_mainpage.*

class RecruiterMainPage:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recruiter_mainpage)
        val  bottomNavigationView=findViewById<BottomNavigationView>(R.id.recruiter_bottomnav)
        val navController=findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController=navController)
        }
    override fun onBackPressed() {
    }

    }
