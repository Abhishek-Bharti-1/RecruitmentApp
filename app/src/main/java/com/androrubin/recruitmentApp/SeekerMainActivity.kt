package com.androrubin.recruitmentApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.androrubin.recruitmentApp.fragments.DashboardFragment
import com.androrubin.recruitmentApp.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SeekerMainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seeker_main)

        val homeFragment = HomeFragment()
        val dashboardFragment = DashboardFragment()

        setCurrentFragment(HomeFragment())


        findViewById<BottomNavigationView>(R.id.bottomNavigation)
            .setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.miHome -> setCurrentFragment(homeFragment)
                    R.id.miDashboard -> setCurrentFragment(dashboardFragment)
                    R.id.miProfile -> startActivity(Intent(this,SeekerProfileView::class.java))
                }
                true
            }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}